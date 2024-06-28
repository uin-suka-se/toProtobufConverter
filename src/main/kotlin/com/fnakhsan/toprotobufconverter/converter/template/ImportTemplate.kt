package com.fnakhsan.toprotobufconverter.converter.template

import com.fnakhsan.toprotobufconverter.converter.template.CommonTemplate.NEW_LINE
import com.fnakhsan.toprotobufconverter.converter.template.CommonTemplate.SEMICOLON

object ImportTemplate {
    private const val IMPORT = "import"
    const val IMPORT_TEMPLATE = "$IMPORT \"%1\$s\"$SEMICOLON$NEW_LINE"

//    https://protobuf.com/docs/descriptors#standard-imports
    const val IMPORT_ANY = "google/protobuf/any.proto"
    const val IMPORT_API =     "google/protobuf/api.proto"
    const val IMPORT_PLUGIN = "google/protobuf/compiler/plugin.proto"
    const val IMPORT_CPP_FEATURES = "google/protobuf/cpp_features.proto"
    const val IMPORT_DESCRIPTOR = "google/protobuf/descriptor.proto"
    const val IMPORT_DURATION = "google/protobuf/duration.proto"
    const val IMPORT_EMPTY = "google/protobuf/empty.proto"
    const val IMPORT_FIELD_MASK = "google/protobuf/field_mask.proto"
    const val IMPORT_JAVA_FEATURES = "google/protobuf/java_features.proto"
    const val IMPORT_SOURCE_CONTEXT = "google/protobuf/source_context.proto"
    const val IMPORT_STRUCT = "google/protobuf/struct.proto"
    const val IMPORT_TIMESTAMP = "google/protobuf/timestamp.proto"
    const val IMPORT_TYPE = "google/protobuf/type.proto"
    const val IMPORT_WRAPPERS = "google/protobuf/wrappers.proto"
}