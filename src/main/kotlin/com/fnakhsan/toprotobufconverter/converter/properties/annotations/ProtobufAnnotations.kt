package com.fnakhsan.toprotobufconverter.converter.properties.annotations

internal sealed class ProtobufAnnotations(
    val classAnnotation: String = EMPTY_ANNOTATION,
    val annotation: String
) {
    object FieldDeprecated : ProtobufAnnotations(
        annotation = "@field:SerializedName(\"%1\$s\")"
    )
}

private const val EMPTY_ANNOTATION = ""