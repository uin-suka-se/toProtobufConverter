package com.fnakhsan.toprotobufconverter.core.delegates

import com.fnakhsan.toprotobufconverter.core.models.ConversionModel

interface PreWriterDelegate {

    fun updateFileBody(
        conversionModel: ConversionModel,
        body: String
    ): String
}

internal class PreWriterDelegateImpl(
    private val indentationDelegate: IndentationDelegate
) : PreWriterDelegate {

    override fun updateFileBody(
        conversionModel: ConversionModel,
        body: String
    ) = indentationDelegate.updateFileBody(body)
}
