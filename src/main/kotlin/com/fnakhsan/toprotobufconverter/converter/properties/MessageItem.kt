package com.fnakhsan.toprotobufconverter.converter.properties

internal data class MessageItem(
    val messageName: String? = null,
    var option: String? = null,
    var messageOption: String? = null,
    var packagePath: String? = null,
    val messageFields: LinkedHashMap<String, MessageField> = LinkedHashMap(),
    val messageImports: HashSet<String> = HashSet()
)
