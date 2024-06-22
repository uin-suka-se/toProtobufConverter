package com.fnakhsan.toprotobufconverter.converter.parser

import com.fnakhsan.toprotobufconverter.converter.utils.MessageConversionHelper

internal class InputDataParser(
    private val messageConversionHelper: MessageConversionHelper,
    private val jsonObjectParser: JsonObjectParser,
    private val jsonArrayParser: JsonArrayParser
) {

    fun parse(jsonModel: JsonModel, classesMap: LinkedHashMap<String?, ClassItem>) {
        val classItem = ClassItem(classGenerateHelper.formatClassName(jsonModel.key))
        if (jsonModel is JsonItem) {
            jsonObjectParser.parseJsonObject(
                jsonModel, classesMap, classItem,
                this::parse,
                this::onArrayHandled
            )
        } else {
            jsonArrayParser.parseJsonArray(
                jsonModel as JsonItemArray,
                classesMap,
                classItem,
                this::parse
            )
        }
        appendClassesMap(classesMap, classItem)
    }

    private fun onArrayHandled(
        jsonItemArray: JsonItemArray,
        classField: ClassField,
        classesMap: LinkedHashMap<String?, ClassItem>
    ) = jsonArrayParser.parseNonEmptyArray(jsonItemArray, classField, classesMap, this::parse)

    private fun appendClassesMap(classesMap: LinkedHashMap<String?, ClassItem>, classItem: ClassItem) {
        if (classesMap.containsKey(classItem.className)) {
            classesMap[classItem.className]?.let {
                classItem.classFields.putAll(it.classFields)
            }
        }
        classesMap[classItem.className] = classItem
    }
}
