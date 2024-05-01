package com.fnakhsan.toprotobufconverter.converter.utils

import com.fnakhsan.toprotobufconverter.converter.properties.DataClassModel
import com.fnakhsan.toprotobufconverter.core.models.ConversionModel
import java.lang.NullPointerException

internal class ProcessingModelResolver {

    fun resolveDataClassModel(model: ConversionModel): DataClassModel = if (model.content?.isNotBlank() == true) {
        if (model.content.contains("List")) {
            DataClassModel.DataClassList(content = model.content, key = model.rootFileName)
        } else {
            DataClassModel.DataClassItem(content = model.content, key = model.rootFileName)
        }
    } else {
        DataClassModel.DataClassItem(content = "", key = model.rootFileName)
    }
}
