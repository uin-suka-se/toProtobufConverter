package com.fnakhsan.toprotobufconverter.converter.utils

import com.fnakhsan.toprotobufconverter.converter.properties.MessageItem
import com.fnakhsan.toprotobufconverter.converter.properties.templates.MessageTemplate
import com.fnakhsan.toprotobufconverter.core.models.ConversionModel
import com.fnakhsan.toprotobufconverter.core.models.FieldModel

internal class MessageTemplateHelper(
    private val messageConversionHelper: MessageConversionHelper
) {
    fun createToString(messageItem: MessageItem) =
        String.format(
            MessageTemplate.TO_STRING,
            messageItem.messageName,
            generateToStringItem(messageItem)
        )

    private fun generateToStringItem(messageItem: MessageItem): String {
        var isFirstField = true
        val fieldToStringStatement = StringBuilder()
        val fields = messageItem.messageFields.keys
        for (field in fields) {
            fieldToStringStatement.append(
                String.format(
                    MessageTemplate.TO_STRING_LINE,
                    messageConversionHelper.lowerCaseFirst(field),
                    messageConversionHelper.formatClassField(field),
                    if (isFirstField) "" else ","
                )
            )
            if (isFirstField) {
                isFirstField = false
            }
        }
        return fieldToStringStatement.toString()
    }

    fun createField(model: FieldModel): String {
        val fieldDeclaration = if (model.visibility == Visibility.NONE) String.format(
            MessageTemplate.FIELD,
            model.classType,
            model.fieldNameFormatted
        ) else (
                String.format(
                    MessageTemplate.FIELD_WITH_VISIBILITY,
                    model.visibility.value,
                    model.classType,
                    model.fieldNameFormatted
                )
                )
        return createAnnotatedField(model.fieldName, model.annotation, fieldDeclaration)
    }

    fun createAutoValueField(model: FieldModel) =
        createAnnotatedField(
            model.fieldName, model.annotation,
            String.format(
                MessageTemplate.FIELD_AUTO_VALUE,
                model.classType,
                model.fieldNameFormatted
            )
        )

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

    fun createJavaRecordClassField(model: FieldModel) =
        createAnnotatedField(
            model.fieldName, model.annotation,
            String.format(
                MessageTemplate.FIELD_JAVA_RECORD,
                model.classType,
                model.fieldNameFormatted
            )
        )

    fun createClassBody(messageItem: ClassItem, classBody: String?) =
        createClassBodyAnnotated(
            messageItem,
            String.format(
                MessageTemplate.CLASS_BODY,
                messageItem.className,
                classBody
            )
        )

    fun createTypeAdapter(messageItem: ClassItem) =
        String.format(MessageTemplate.TYPE_ADAPTER, messageItem.className)

    fun createClassBodyAbstract(messageItem: ClassItem, classBody: String?) =
        createClassBodyAnnotated(
            messageItem,
            String.format(
                MessageTemplate.CLASS_BODY_ABSTRACT,
                messageItem.className,
                classBody
            )
        )

    fun createClassBodyRecords(messageItem: ClassItem, classBody: String?) =
        createClassBodyAnnotated(
            messageItem,
            String.format(
                MessageTemplate.CLASS_BODY_RECORDS,
                messageItem.className,
                classBody
            )
        )

    fun createClassBodyKotlinDataClass(
        messageItem: MessageItem,
        classBody: String?,
        conversionModel: ConversionModel
    ) = createClassBodyAnnotated(
        messageItem,
        String.format(
            generateKotlinClass(conversionModel),
            messageItem.messageName,
            classBody
        )
    )

    private fun generateKotlinClass(conversionModel: ConversionModel): String {
        val body = if (conversionModel.useKotlinParcelable) {
            MessageTemplate.CLASS_BODY_KOTLIN_DTO_PARCELABLE
        } else {
            MessageTemplate.CLASS_BODY_KOTLIN_DTO
        }
        return if (conversionModel.useKotlinDataClass) {
            body
        } else {
            body.replace(MessageTemplate.KOTLIN_DATA_CLASS, "")
        }
    }

    fun createClassItem(
        packagePath: String?,
        imports: String?,
        body: String?
    ) = if (packagePath?.isNotEmpty() == true) {
        if (null != imports && imports.isNotEmpty()) {
            String.format(
                MessageTemplate.CLASS_ROOT_IMPORTS,
                packagePath,
                imports,
                body
            )
        } else {
            String.format(
                MessageTemplate.CLASS_ROOT,
                packagePath,
                body
            )
        }
    } else {
        String.format(MessageTemplate.CLASS_ROOT_NO_PACKAGE, body)
    }

    fun createClassItemWithoutSemicolon(
        packagePath: String?,
        imports: String?,
        body: String?
    ) = if (packagePath?.isNotEmpty() == true) {
        if (null != imports && imports.isNotEmpty()) {
            String.format(
                MessageTemplate.CLASS_ROOT_IMPORTS_WITHOUT_SEMICOLON,
                packagePath,
                imports,
                body
            )
        } else {
            String.format(
                MessageTemplate.CLASS_ROOT_WITHOUT_SEMICOLON,
                packagePath,
                body
            )
        }
    } else {
        String.format(MessageTemplate.CLASS_ROOT_NO_PACKAGE, body)
    }

    private fun createClassBodyAnnotated(
        messageItem: ClassItem,
        messageItemBody: String
    ) = if (messageItem.classAnnotation?.isNotEmpty() == true) {
        String.format(
            MessageTemplate.CLASS_BODY_ANNOTATED,
            messageItem.classAnnotation,
            messageItemBody
        )
    } else {
        messageItemBody
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
