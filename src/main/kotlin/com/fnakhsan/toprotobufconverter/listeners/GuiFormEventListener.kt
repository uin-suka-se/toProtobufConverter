package com.fnakhsan.toprotobufconverter.listeners

import com.fnakhsan.toprotobufconverter.core.models.ConversionModel

internal interface GuiFormEventListener {
    fun onDataObtained(model: ConversionModel)
}
