package com.fnakhsan.toprotobufconverter.converter.postprocessing.common

import com.fnakhsan.toprotobufconverter.converter.postprocessing.BasePostProcessor
import com.fnakhsan.toprotobufconverter.converter.properties.MessageItem
import com.fnakhsan.toprotobufconverter.converter.properties.templates.MessageTemplate.PROTO2
import com.fnakhsan.toprotobufconverter.converter.properties.templates.MessageTemplate.PROTO3
import com.fnakhsan.toprotobufconverter.converter.utils.MessageConversionHelper
import com.fnakhsan.toprotobufconverter.core.models.ConversionModel
import com.fnakhsan.toprotobufconverter.core.models.FieldModel
import com.fnakhsan.toprotobufconverter.core.models.VersionVW.*
import com.fnakhsan.toprotobufconverter.converter.utils.MessageTemplateHelper

internal class ProtobufMessagePostProcessor(
    conversionHelper: MessageConversionHelper,
    messageTemplateHelper: MessageTemplateHelper,
) : BasePostProcessor(conversionHelper, messageTemplateHelper) {

    override fun applyVersion(conversionModel: ConversionModel) = when (conversionModel.versionEnum) {
        is Proto2 -> {
            PROTO2
        }

        is Proto3 -> {
            PROTO3
        }
    }

    override fun proceedMessageBody(messageItem: MessageItem, conversionModel: ConversionModel): String? {
        val messageBodyBuilder = StringBuilder()
        val messageFields = messageItem.messageFields
        for (objectName in messageFields.keys) {
            messageBodyBuilder.append(
                messageTemplateHelper.createProtobufMessageField(
                    conversionModel,
                    FieldModel(
                        fieldType = if (conversionModel.versionEnum.propertyName == "Proto2") messageFields[objectName]?.getProto2() else messageFields[objectName]?.getProto3(),
                        option = messageItem.fieldOption,
                        fieldName = objectName,
                        fieldNameFormatted = conversionHelper.formatMessageField(objectName)
                    )
                )
            )
        }
        conversionHelper.updateMessageModel(messageBodyBuilder)
        return messageBodyBuilder.toString()
    }

    override fun createMessageTemplate(
        messageItem: MessageItem,
        messageBody: String?,
        conversionModel: ConversionModel
    ): String = messageTemplateHelper.createMessageItem(
        conversionModel.versionEnum.propertyName,
        messageItem.packagePath,
        messageItem.fileOption,
        messageBody
    )
}