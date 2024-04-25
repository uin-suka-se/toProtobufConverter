package com.fnakhsan.toprotobufconverter.converter.postprocessing

import com.fnakhsan.toprotobufconverter.converter.postprocessing.common.ProtobufMessagePostProcessor
import com.fnakhsan.toprotobufconverter.core.models.ConversionModel

internal class PostProcessorFactory(
    private val protobufMessagePostProcessor: ProtobufMessagePostProcessor,
) {
    fun createPostProcessor(
        conversionModel: ConversionModel
    ): BasePostProcessor = protobufMessagePostProcessor
}
