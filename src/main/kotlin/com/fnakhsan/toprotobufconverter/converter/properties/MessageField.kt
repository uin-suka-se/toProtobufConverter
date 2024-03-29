package com.fnakhsan.toprotobufconverter.converter.properties

import com.robohorse.robopojogenerator.properties.templates.ArrayItemsTemplate

internal class MessageField(
    private var messageEnum: MessageEnum? = null,
    var messageName: String? = null,
    var messageField: MessageField? = null
) {
    fun getProto2(): String? {
        return if (messageField != null) {
            String.format(
                ArrayItemsTemplate.LIST_OF_ITEM,
                messageField?.getProto2()
            )
        } else {
            messageName ?: messageEnum?.proto2
        }
    }

    fun getProto3(): String? {
        return if (messageField != null) {
            String.format(
                ArrayItemsTemplate.LIST_OF_ITEM,
                messageField?.getProto3()
            )
        } else {
            messageName ?: messageEnum?.proto3
        }
    }
}
