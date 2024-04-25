package com.fnakhsan.toprotobufconverter.converter.properties.templates

internal object MessageTemplate {
    const val PROTO2 = "proto2"
    const val PROTO3 = "proto3"
    const val KOTLIN_DATA_CLASS = "data"

    const val NEW_LINE = "\n"
    const val TAB = "\t"
    const val SEMICOLON = ";"
    const val INDENT = "  "

    const val CLASS_BODY_KOTLIN_DTO = "$KOTLIN_DATA_CLASS class %1\$s" +
        "(" + NEW_LINE +
        "%2\$s" + NEW_LINE +
        ")"

    const val CLASS_BODY_KOTLIN_DTO_PARCELABLE = "@Parcelize\n$KOTLIN_DATA_CLASS class %1\$s" +
        "(" + NEW_LINE +
        "%2\$s" + NEW_LINE +
        ") : Parcelable"

    const val CLASS_BODY_ANNOTATED = "%1\$s" + NEW_LINE +
        "%2\$s"

    const val CLASS_ROOT_IMPORTS = (
        "package %1\$s;" + NEW_LINE + NEW_LINE +
            "%2\$s" + NEW_LINE +
            "%3\$s"
        )

    const val CLASS_ROOT_IMPORTS_WITHOUT_SEMICOLON = (
        "package %1\$s" + NEW_LINE + NEW_LINE +
            "%2\$s" + NEW_LINE +
            "%3\$s"
        )

    const val CLASS_ROOT = "package %1\$s;" + NEW_LINE + NEW_LINE +
        "%2\$s" + NEW_LINE

    const val CLASS_ROOT_WITHOUT_SEMICOLON = "package %1\$s" + NEW_LINE + NEW_LINE +
        "%2\$s" + NEW_LINE

    const val CLASS_ROOT_NO_PACKAGE = "%1\$s" + NEW_LINE

    const val MESSAGE_ROOT = "syntax = \"%1\$s\"$SEMICOLON$NEW_LINE%2\$s$SEMICOLON$NEW_LINE%2\$s$NEW_LINE"

    const val MESSAGE_ROOT_NO_PACKAGE = "syntax = \"%1\$s\"$SEMICOLON$NEW_LINE%2\$s$NEW_LINE"

    const val FIELD_DEFAULT = "$INDENT%1\$s %2\$s = %3\$s;$NEW_LINE"
    const val FIELD_DEFAULT_OPTIONS = "$INDENT%1\$s %2\$s = %3\$s [%4\$s];$NEW_LINE"
}
