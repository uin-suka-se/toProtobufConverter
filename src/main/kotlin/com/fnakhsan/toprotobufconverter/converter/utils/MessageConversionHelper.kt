package com.fnakhsan.toprotobufconverter.converter.utils

import com.fnakhsan.toprotobufconverter.converter.properties.ReservedWords
import com.fnakhsan.toprotobufconverter.converter.properties.templates.MessageTemplate
import com.fnakhsan.toprotobufconverter.converter.properties.templates.PreferencesTemplate
import com.fnakhsan.toprotobufconverter.core.WrongFileNameException
import com.google.common.base.CaseFormat


class MessageConversionHelper {
    fun validateClassContent(content: String): String {
        try {
            ClassObject(content)
        } catch (exception: Exception) {
            return try {
                val jsonArray = ClassArray(content)
                if (jsonArray.length() > 0) {
                    val jsonObject = jsonArray.optClassObject(0)
                    if (jsonObject.keySet().isEmpty()) {
                        throw ClassStructureException()
                    }
                    jsonObject.toString()
                } else {
                    throw ClassStructureException()
                }
            } catch (arrayException: Exception) {
                throw ClassStructureException()
            }
        }
        return content
    }

    fun validateFileName(name: String?) {
        if (name?.matches(NAME_PATTERN) != true) {
            throw WrongFileNameException()
        }
    }

    fun updateMessageBody(messageBody: String?): String? {
        if (null != messageBody && messageBody.isNotEmpty()) {
            val lastIndex = messageBody.length - 1
            if (messageBody[lastIndex] == '\n') {
                return messageBody.substring(0, lastIndex)
            }
        }
        return messageBody
    }

    fun formatMessageName(name: String) = upperCaseName(proceedField(name))

    fun getMessageNameWithItemPostfix(name: String) =
        String.format(PreferencesTemplate.ITEM_NAME, upperCaseName(proceedField(name)))

    fun upperCaseName(name: String) = if (name.length > 1) {
        Character.toUpperCase(name.first()).toString() + name.substring(1)
    } else {
        name.uppercase()
    }

    fun formatMessageField(name: String) = lowerCaseFirst(proceedField(name), forceLowerCase = true)

    fun lowerCaseFirst(name: String, forceLowerCase: Boolean = false) = if (name.length > 1) {
        Character.toLowerCase(name.first()).toString() + name.substring(1)
    } else if (forceLowerCase) {
        name.lowercase()
    } else {
        name
    }

    internal fun setAnnotations(
        messageItem: MessageItem,
        messageAnnotation: String,
        annotation: String,
        imports: Array<String>
    ) {
        messageItem.messageAnnotation = messageAnnotation
        messageItem.annotation = annotation
        messageItem.messageImports.addAll(imports)
    }

    fun updateMessageModel(messageBodyBuilder: StringBuilder) {
        if (messageBodyBuilder.isEmpty()) {
            messageBodyBuilder.append(MessageTemplate.FIELD_KOTLIN_DOT_DEFAULT)
        } else {
            messageBodyBuilder.deleteCharAt(messageBodyBuilder.lastIndexOf(","))
        }
    }

    fun proceedField(fieldName: String): String {
        var objectName = fieldName
        objectName = objectName
            .replace("[^A-Za-z0-9]".toRegex(), "_")
            .replace("_{2,}".toRegex(), "_")
        val isDigitFirst = (
                objectName.isNotBlank() && Character.isDigit(objectName.first()) ||
                        objectName.length > 1 && objectName.first() == '_' &&
                        Character.isDigit(objectName[1])
                )
        if (objectName.isBlank() || isDigitFirst || ReservedWords.WORDS_SET.contains(objectName)) {
            objectName = "json_member_$objectName"
        }
        objectName = objectName.replace("([A-Z])".toRegex(), "_$1")
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, objectName)
    }
}

private val NAME_PATTERN = "^[a-z0-9_]*$".toRegex()