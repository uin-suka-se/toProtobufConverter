package com.fnakhsan.toprotobufconverter.converter.utils

import com.fnakhsan.toprotobufconverter.core.WrongFileNameException


class MessageConversionHelper {

    fun validateFileName(name: String?) {
        if (name?.matches(NAME_PATTERN) != true) {
            throw WrongFileNameException()
        }
    }
}

private val NAME_PATTERN = "^[a-z0-9_]*$".toRegex()