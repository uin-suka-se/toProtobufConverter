package com.fnakhsan.toprotobufconverter.converter

import com.fnakhsan.toprotobufconverter.converter.properties.MessageItem
import com.fnakhsan.toprotobufconverter.converter.utils.ProcessingModelResolver
import com.fnakhsan.toprotobufconverter.core.models.ConversionModel
import com.fnakhsan.toprotobufconverter.converter.parser.InputDataParser

internal class ProtobufConverter(
    private val processor: InputDataParser,
    private val processingModelResolver: ProcessingModelResolver
) {

    fun convert(model: ConversionModel): Set<MessageItem> {
        val map = LinkedHashMap<String?, MessageItem>()
        processor.parse(
            processingModelResolver.resolveDataClassModel(model),
            map
        )
        return HashSet(map.values)
    }
}
