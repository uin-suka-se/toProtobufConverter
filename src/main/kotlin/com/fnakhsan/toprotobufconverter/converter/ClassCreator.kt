package com.fnakhsan.toprotobufconverter.converter

import com.fnakhsan.toprotobufconverter.converter.filewriter.FileDelegateFactory
import com.fnakhsan.toprotobufconverter.core.models.ConversionModel
import com.fnakhsan.toprotobufconverter.core.models.ProjectModel


internal class ClassCreator(
    private val protobufConverter: ProtobufConverter,
    private val fileWriteFactory: FileDelegateFactory
) {

    fun generateFiles(
        conversionModel: ConversionModel,
        projectModel: ProjectModel
    ) = fileWriteFactory.createFileWriter(
        conversionModel
    ).writeFiles(
        protobufConverter.convert(conversionModel),
        conversionModel,
        projectModel
    )
}
