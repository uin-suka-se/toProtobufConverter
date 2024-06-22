package com.robohorse.robopojogenerator.parser

import com.fnakhsan.toprotobufconverter.converter.properties.MessageEnum.*

internal abstract class MessageFieldsParser {

    fun parseField(targetItem: Any) {
        when (targetItem) {
            is Array<*> -> onRepeatedTypeRecognised()
            is List<*> -> onRepeatedTypeRecognised()
            is String -> onPlainTypeRecognised(STRING)
            is Int -> onPlainTypeRecognised(INTEGER)
            is Double -> onPlainTypeRecognised(DOUBLE)
            is Float -> onPlainTypeRecognised(FLOAT)
            is Long -> onPlainTypeRecognised(LONG)
            is Boolean -> onPlainTypeRecognised(BOOLEAN)
            else -> onPlainTypeRecognised(ANY)
        }
    }

    abstract fun onPlainTypeRecognised(classEnum: ClassEnum?)

    abstract fun onJsonTypeRecognised()

    abstract fun onJsonArrayTypeRecognised()
}
