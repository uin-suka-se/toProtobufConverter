package com.fnakhsan.toprotobufconverter.core

open class PluginException(
    val header: String,
    message: String?
) : Exception(message)

class FileWriteException(
    message: String?
) : PluginException("File creation exception:", message)

class SourceException :
    PluginException(
        "Language is not available:",
        "You should choose another language to be converted into Protocol Buffers Schema files, before call this plugin"
    )

class KotlinStructureException :
    PluginException("Kotlin exception:", "incorrect structure, try on kotlin data class")

class WrongFileNameException :
    PluginException("Wrong file name:", "Files cannot be empty and must be named with lower_snake_case")

class PathException :
    PluginException(
        "No directory was selected:",
        "You should choose directory for Protocol Buffers files, before call this plugin"
    )