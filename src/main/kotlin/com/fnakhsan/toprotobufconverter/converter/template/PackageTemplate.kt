package com.fnakhsan.toprotobufconverter.converter.template

import com.fnakhsan.toprotobufconverter.converter.template.CommonTemplate.NEW_LINE
import com.fnakhsan.toprotobufconverter.converter.template.CommonTemplate.SEMICOLON
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.OPTION
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.PACKAGE

object PackageTemplate {
    const val PACKAGE_TEMPLATE = "$PACKAGE %1\$s$SEMICOLON$NEW_LINE"
    const val JAVA_PACKAGE = "$OPTION java_package = \"%1\$s\"$SEMICOLON$NEW_LINE"
}