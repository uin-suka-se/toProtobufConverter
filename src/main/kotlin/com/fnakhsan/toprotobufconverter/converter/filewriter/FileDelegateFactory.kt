package com.fnakhsan.toprotobufconverter.converter.filewriter

import com.fnakhsan.toprotobufconverter.converter.filewriter.common.CommonFileWriterDelegate
import com.fnakhsan.toprotobufconverter.converter.filewriter.common.ProtobufSingleFileWriterDelegate
import com.fnakhsan.toprotobufconverter.core.models.ConversionModel
import com.robohorse.robopojogenerator.models.GenerationModel

internal class FileDelegateFactory(
    private val commonFileWriterDelegate: CommonFileWriterDelegate,
    private val kotlinSingleFileWriterDelegate: ProtobufSingleFileWriterDelegate
) {

    fun createFileWriter(conversionModel: ConversionModel): BaseWriterDelegate =
        if (conversionModel.useKotlin && conversionModel.useKotlinSingleDataClass) {
            kotlinSingleFileWriterDelegate
        } else {
            commonFileWriterDelegate
        }
}
