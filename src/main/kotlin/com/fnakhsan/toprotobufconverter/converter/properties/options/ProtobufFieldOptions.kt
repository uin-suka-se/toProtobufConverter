package com.fnakhsan.toprotobufconverter.converter.properties.options

internal sealed class ProtobufFieldOptions(
    val fieldOption: String
) {
    object FieldDefault : ProtobufFieldOptions(
        fieldOption = "default = %1\$s"
    )

    object FieldDeprecated : ProtobufFieldOptions(
        fieldOption = "deprecated = %1\$s"
    )

    object FieldPacked : ProtobufFieldOptions(
        fieldOption = "packed = %1\$s"
    )
}