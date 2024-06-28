package com.fnakhsan.toprotobufconverter.converter.template

import com.fnakhsan.toprotobufconverter.converter.template.CommonTemplate.NEW_LINE
import com.fnakhsan.toprotobufconverter.converter.template.CommonTemplate.SEMICOLON

object VersionTemplate {
    const val PROTO2 = "proto2"
    const val PROTO3 = "proto3"
    const val EDITION_2023 = "2023"
    const val VERSION_TEMPLATE = "syntax = \"%1\$s\"$SEMICOLON$NEW_LINE"
    const val EDITION_TEMPLATE = "edition = \"%1\$s\"$SEMICOLON$NEW_LINE"
}