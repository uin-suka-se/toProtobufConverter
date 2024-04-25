package com.fnakhsan.toprotobufconverter.converter.utils

import com.fnakhsan.toprotobufconverter.converter.properties.DataClassModel
import com.fnakhsan.toprotobufconverter.core.models.ConversionModel

internal class ProcessingModelResolver {

    fun resolveJsonModel(model: ConversionModel): DataClassModel =
        try {
            model.content?.contains("List<>")
            DataClassModel.DataClassItem(item = model.content, key = model.rootFileName)
        } catch (e: Exception) {
            JsonModel.JsonItemArray(
                jsonObject = JSONArray(model.content),
                key = model.rootClassName
            )
        }
}
