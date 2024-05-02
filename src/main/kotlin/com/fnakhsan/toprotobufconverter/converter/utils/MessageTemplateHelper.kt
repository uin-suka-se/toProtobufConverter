package com.fnakhsan.toprotobufconverter.converter.utils

import com.fnakhsan.toprotobufconverter.converter.properties.MessageItem
import com.fnakhsan.toprotobufconverter.converter.properties.templates.MessageTemplate
import com.fnakhsan.toprotobufconverter.core.models.ConversionModel
import com.fnakhsan.toprotobufconverter.core.models.FieldModel

internal class MessageTemplateHelper(
    private val messageConversionHelper: MessageConversionHelper
) {

    fun createProtobufMessageField(conversionModel: ConversionModel, model: FieldModel) =
        if (conversionModel.kotlinNullableFields) {
            createAnnotatedField(
                model.fieldName, model.annotation,
                String.format(
                    MessageTemplate.FIELD_KOTLIN_DTO,
                    model.fieldNameFormatted,
                    model.classType
                ).replace(">", "?>")
            )
        } else {
            createAnnotatedField(
                model.fieldName, model.annotation,
                String.format(
                    MessageTemplate.FIELD_KOTLIN_DTO_NON_NULL,
                    model.fieldNameFormatted,
                    model.classType
                )
            )
        }

    private fun createAnnotatedField(
        name: String?,
        annotation: String?,
        field: String
    ) = if (null != annotation && annotation.isNotEmpty()) {
        String.format(
            MessageTemplate.FIELD_ANNOTATED,
            String.format(annotation, name),
            field
        )
    } else {
        field
    }

    fun createMessageItem(
        protobufVersion: String?,
        packagePath: String?,
        fileOptions: String?,
        body: String?
    ) = if (packagePath?.isNotEmpty() == true) {
        String.format(
            MessageTemplate.MESSAGE_ROOT,
            protobufVersion,
            body
        )
//        if (!options.isNullOrEmpty()) {
//            String.format(
//                MessageTemplate.CLASS_ROOT_IMPORTS,
//                packagePath,
//                imports,
//                body
//            )
//        } else {
//            String.format(
//                MessageTemplate.CLASS_ROOT,
//                packagePath,
//                body
//            )
//        }
    } else {
        String.format(MessageTemplate.MESSAGE_ROOT_NO_PACKAGE, protobufVersion, body)
    }
}
