package com.fnakhsan.toprotobufconverter.converter.parser

import com.fnakhsan.toprotobufconverter.converter.utils.MessageConversionHelper
import com.robohorse.robopojogenerator.parser.MessageFieldsParser
import org.json.JSONArray
import org.json.JSONObject

internal class JsonObjectParser(
    private val messageConversionHelper: MessageConversionHelper
) {

    fun parseJsonObject(
        jsonItem: JsonItem,
        classesMap: LinkedHashMap<String?, ClassItem>,
        classItem: ClassItem,
        jsonCallback: (innerJsonItem: JsonItem, classesMap: LinkedHashMap<String?, ClassItem>) -> Unit,
        arrayCallback: (
            innerJsonItem: JsonItemArray,
            classField: ClassField,
            classesMap: LinkedHashMap<String?, ClassItem>
        ) -> Unit
    ) {
        for (jsonObjectKey in jsonItem.jsonObject.keySet()) {
            val itemObject = jsonItem.jsonObject[jsonObjectKey]
            val classFieldsParser = object : MessageFieldsParser() {

                override fun onPlainTypeRecognised(classEnum: ClassEnum?) {
                    classItem.classFields[jsonObjectKey] = ClassField(classEnum)
                }

                override fun onJsonTypeRecognised() {
                    val className = classGenerateHelper.formatClassName(jsonObjectKey)
                    val classField = ClassField(null, className)
                    val innerJsonItem = JsonItem(jsonObjectKey, (itemObject as JSONObject))
                    classItem.classFields[jsonObjectKey] = classField
                    jsonCallback.invoke(innerJsonItem, classesMap)
                }

                override fun onJsonArrayTypeRecognised() {
                    val jsonArray = itemObject as JSONArray
                    classItem.classImports.add(ImportsTemplate.LIST)
                    val classField = ClassField()
                    if (jsonArray.length() == 0) {
                        classField.classField = ClassField(ClassEnum.OBJECT)
                        classItem.classFields[jsonObjectKey] = classField
                    } else {
                        val jsonItemArray = JsonItemArray(jsonObjectKey, itemObject)
                        arrayCallback.invoke(jsonItemArray, classField, classesMap)
                        classItem.classFields[jsonObjectKey] = classField
                    }
                }
            }
            classFieldsParser.parseField(itemObject)
        }
    }
}
