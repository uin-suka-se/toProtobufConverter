package com.fnakhsan.toprotobufconverter.core

open class PluginException(
    val header: String,
    message: String?
) : Exception(message)

class FileWriteException(
    message: String?
) : PluginException("File creation exception:", message)

class KotlinStructureException :
    PluginException("Kotlin exception:", "incorrect structure")

class PathException :
    PluginException(
        "No directory was selected:",
        "You should choose directory for Protocol Buffers files, before call this plugin"
    )

class WrongClassNameException :
    PluginException("Wrong class name:", "you should set root class name")