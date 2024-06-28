package com.fnakhsan.toprotobufconverter.converter.template

import com.fnakhsan.toprotobufconverter.converter.template.CommonTemplate.CLOSE_CURLY_BRACKET
import com.fnakhsan.toprotobufconverter.converter.template.CommonTemplate.NEW_LINE
import com.fnakhsan.toprotobufconverter.converter.template.CommonTemplate.OPEN_CURLY_BRACKET
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.MESSAGE

object MessageTemplate {
    const val MESSAGE_TEMPLATE = "$MESSAGE %1\$s $OPEN_CURLY_BRACKET$NEW_LINE%2\$s$NEW_LINE$CLOSE_CURLY_BRACKET"
}