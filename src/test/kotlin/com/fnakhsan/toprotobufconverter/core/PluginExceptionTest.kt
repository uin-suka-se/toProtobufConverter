package com.fnakhsan.toprotobufconverter.core

import kotlin.test.Test

internal class PluginExceptionTest {
    @Test
    fun check_FileWriteException() {
        val message = "Something was wrong..."
        val exception = FileWriteException(message)
        kotlin.test.assertEquals(message, exception.message)
        kotlin.test.assertEquals("File creation exception:", exception.header)
    }

    @Test
    fun check_KotlinStructureException() {
        val exception = KotlinStructureException()
        kotlin.test.assertEquals("incorrect structure, try on kotlin data class", exception.message)
        kotlin.test.assertEquals("Kotlin exception:", exception.header)
    }

    @Test
    fun check_WrongFileNameException() {
        val exception = WrongFileNameException()
        kotlin.test.assertEquals("Files should be named with lower_snake_case", exception.message)
        kotlin.test.assertEquals("Wrong file name:", exception.header)
    }
}