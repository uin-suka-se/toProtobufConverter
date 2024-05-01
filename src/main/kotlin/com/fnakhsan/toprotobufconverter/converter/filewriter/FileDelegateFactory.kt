package com.fnakhsan.toprotobufconverter.converter.filewriter

import com.fnakhsan.toprotobufconverter.converter.filewriter.common.CommonFileWriterDelegate
import com.fnakhsan.toprotobufconverter.converter.filewriter.common.ProtobufSingleFileWriterDelegate
import com.fnakhsan.toprotobufconverter.core.models.ConversionModel

internal class FileDelegateFactory(
    private val commonFileWriterDelegate: CommonFileWriterDelegate,
    private val protobufSingleFileWriterDelegate: ProtobufSingleFileWriterDelegate
) {
    private val useProtobuf = true
    private val useProtobufSingleFile = true
    fun createFileWriter(conversionModel: ConversionModel): BaseWriterDelegate =
        if (useProtobuf && useProtobufSingleFile) {
            protobufSingleFileWriterDelegate
        } else {
            commonFileWriterDelegate
        }
}
