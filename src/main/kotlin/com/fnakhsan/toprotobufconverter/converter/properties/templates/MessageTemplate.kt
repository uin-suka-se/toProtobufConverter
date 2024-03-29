package com.fnakhsan.toprotobufconverter.converter.properties.templates

internal object MessageTemplate {
    const val KOTLIN_DATA_CLASS = "data"
    const val NEW_LINE = "\n"
    const val TAB = "\t"
    const val PROTO2 = "syntax = \"proto2\";"
    const val PROTO3 = "syntax = \"proto3\";"
    const val PACKED = "[packed = %1\$s]"
    const val DEPRECATED = "[deprecated = %1\$s]"

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

    const val FIELD = "$TAB%1\$s %2\$s;$NEW_LINE"
    const val FIELD_WITH_VISIBILITY = "$TAB%1\$s %2\$s %3\$s;$NEW_LINE"
    const val FIELD_AUTO_VALUE = TAB + "public abstract %1\$s %2\$s();" + NEW_LINE
    const val FIELD_KOTLIN_DTO = TAB + "val %1\$s: %2\$s? = null" + "," + NEW_LINE
    const val FIELD_KOTLIN_DTO_NON_NULL = TAB + "val %1\$s: %2\$s" + "," + NEW_LINE
    const val FIELD_JAVA_RECORD = "$TAB%1\$s %2\$s,$NEW_LINE"
    const val FIELD_PROTO_DEFAULT = TAB + "any: Any? = %1\$s;"
    const val FIELD_ANNOTATED = "$NEW_LINE$TAB%1\$s$NEW_LINE%2\$s"
}
