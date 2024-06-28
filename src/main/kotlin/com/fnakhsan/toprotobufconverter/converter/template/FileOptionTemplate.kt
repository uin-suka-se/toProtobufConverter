package com.fnakhsan.toprotobufconverter.converter.template

import com.fnakhsan.toprotobufconverter.converter.template.CommonTemplate.NEW_LINE
import com.fnakhsan.toprotobufconverter.converter.template.CommonTemplate.SEMICOLON
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.OPTION

object FileOptionTemplate {
    const val JAVA_MULTIPLE_FILE = "$OPTION java_multiple_files = %1\$b$SEMICOLON$NEW_LINE"
    const val JAVA_OUTER_CLASSNAME = "$OPTION java_outer_classname = \"%1\$s\"$SEMICOLON$NEW_LINE"
}