package com.fnakhsan.toprotobufconverter.converter.properties

internal data class MessageItem(
    var packagePath: String? = null,
    val messageImports: HashSet<String> = HashSet(),
    val messageName: String? = null,
    var fileOption: String? = null,
    var fieldLabel: String? = null,
    val messageFields: LinkedHashMap<String, MessageField> = LinkedHashMap(),
    var fieldOption: String? = null,
    var lost: List<Int> = listOf(1,2,3)
)
