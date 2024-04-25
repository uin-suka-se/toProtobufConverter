package com.fnakhsan.toprotobufconverter.converter.properties

internal sealed class DataClassModel(
    open val key: String
) {
    data class DataClassItem(
        override val key: String,
        val content: String
    ) : DataClassModel(key)

    data class DataClassList(
        override val key: String,
        val content: String
    ) : DataClassModel(key)
}
