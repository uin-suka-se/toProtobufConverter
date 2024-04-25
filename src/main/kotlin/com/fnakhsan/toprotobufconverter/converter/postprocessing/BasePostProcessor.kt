package com.fnakhsan.toprotobufconverter.converter.postprocessing

import com.fnakhsan.toprotobufconverter.converter.properties.MessageItem
import com.fnakhsan.toprotobufconverter.converter.properties.templates.MessageTemplate
import com.fnakhsan.toprotobufconverter.converter.utils.MessageConversionHelper
import com.fnakhsan.toprotobufconverter.core.models.ConversionModel
import com.fnakhsan.toprotobufconverter.converter.utils.MessageTemplateHelper

internal abstract class BasePostProcessor(
    protected val conversionHelper: MessageConversionHelper,
    protected val messageTemplateHelper: MessageTemplateHelper
) {

    fun proceed(
        messageItem: MessageItem,
        conversionModel: ConversionModel
    ): String {
        applyVersion(conversionModel)
        return proceedMessage(messageItem, conversionModel)
    }

    abstract fun applyVersion(
        conversionModel: ConversionModel
    ): String

    abstract fun proceedMessageBody(
        messageItem: MessageItem,
        conversionModel: ConversionModel
    ): String?

    abstract fun createMessageTemplate(
        messageItem: MessageItem,
        messageBody: String?,
        conversionModel: ConversionModel
    ): String

    private fun proceedMessage(
        messageItem: MessageItem,
        conversionModel: ConversionModel
    ): String {
        val messageBody = conversionHelper.updateMessageBody(
            proceedMessageBody(messageItem, conversionModel)
        )
        val messageTemplate = createMessageTemplate(messageItem, messageBody, conversionModel)
        return createMessageItemText(
            conversionModel.versionEnum.propertyName,
            messageItem.packagePath,
            messageTemplate
        )
    }

//    open fun proceedMessageVersion(
//        conversionModel: ConversionModel
//    ): StringBuilder {
//        val importsBuilder = StringBuilder()
//        for (importItem in imports) {
//            importsBuilder.append(importItem)
//            importsBuilder.append(MessageTemplate.NEW_LINE)
//        }
//        return importsBuilder
//    }

    open fun createMessageItemText(
        protobufVersion: String?,
        packagePath: String?,
        fileOptions: String?,
        messageTemplate: String?
    ) = messageTemplateHelper.createMessageItem(
        protobufVersion,
        packagePath,
        fileOptions,
        messageTemplate
    )

    open fun proceedFileOptions(
        imports: HashSet<String>,
        conversionModel: ConversionModel
    ): StringBuilder {
        val importsBuilder = StringBuilder()
        for (importItem in imports) {
            importsBuilder.append(importItem)
            importsBuilder.append(MessageTemplate.NEW_LINE)
        }
        return importsBuilder
    }
}
