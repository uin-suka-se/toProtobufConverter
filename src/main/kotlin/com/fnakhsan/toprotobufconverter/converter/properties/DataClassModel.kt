package com.fnakhsan.toprotobufconverter.converter.properties

internal sealed class DataClassModel(
    open val key: String
) {
    data class JsonItem(
        override val key: String,
        val jsonObject: JSONObject
    ) : JsonModel(key)

    data class JsonItemArray(
        override val key: String,
        val jsonObject: JSONArray
    ) : JsonModel(key)
}
