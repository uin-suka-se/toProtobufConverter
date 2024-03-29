package com.fnakhsan.toprotobufconverter.converter.properties

internal enum class MessageEnum(
    val proto2: String,
    val proto3: String,
) {
    DOUBLE("double", "double"),
    FLOAT("float", "float"),
    INTEGER("int32", "int32"),
    UINTEGER("uint32", "uint32"),
    SINTEGER("sint32", "sint32"),
    FIXEDINTEGER("fixed32", "fixed32"),
    SFIXEDINTEGER("sfixed32", "sfixed32"),
    LONG("int64", "int64"),
    ULONG("uint64", "uint64"),
    SLONG("sint64", "sint64"),
    FIXEDLONG("fixed64", "fixed64"),
    SFIXEDLONG("sfixed64", "sfixed64"),
    BOOLEAN("Boolean", "boolean"),
    STRING("String", "string"),
    BYTES("Double", "bytes");
}
