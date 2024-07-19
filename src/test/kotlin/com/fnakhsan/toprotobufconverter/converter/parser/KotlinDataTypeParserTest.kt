package com.fnakhsan.toprotobufconverter.converter.parser

import com.fnakhsan.toprotobufconverter.core.models.NumericPreferencesVM
import com.fnakhsan.toprotobufconverter.utils.DummyModel
import org.junit.Test
import kotlin.test.assertEquals


class KotlinDataTypeParserTest {

    private val kotlinDataTypeParser: KotlinDataTypeParser = KotlinDataTypeParser()
    private lateinit var expectedValue: String
    private lateinit var actualValue: String

    @Test
    fun parseBooleanDataType() {
        val field: Boolean = true
        expectedValue = "bool"
        actualValue = kotlinDataTypeParser.parseDataType(field::class.simpleName!!, numericPref = NumericPreferencesVM.UseDefault())
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun parseShortDataType() {
        val field: Short = 0
        expectedValue = "int32"
        actualValue = kotlinDataTypeParser.parseDataType(field::class.simpleName!!, numericPref = NumericPreferencesVM.UseDefault())
        assertEquals(expectedValue, actualValue)

        expectedValue = "uint32"
        actualValue = kotlinDataTypeParser.parseDataType(field::class.simpleName!!, numericPref = NumericPreferencesVM.UseUInt())
        assertEquals(expectedValue, actualValue)

        expectedValue = "sint32"
        actualValue = kotlinDataTypeParser.parseDataType(field::class.simpleName!!, numericPref = NumericPreferencesVM.UseSInt())
        assertEquals(expectedValue, actualValue)

        expectedValue = "fixed32"
        actualValue = kotlinDataTypeParser.parseDataType(field::class.simpleName!!, numericPref = NumericPreferencesVM.UseFixed())
        assertEquals(expectedValue, actualValue)

        expectedValue = "sfixed32"
        actualValue = kotlinDataTypeParser.parseDataType(field::class.simpleName!!, numericPref = NumericPreferencesVM.UseSFixed())
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun parseIntegerDataType() {
        val field: Int = 0
        expectedValue = "int32"
        actualValue = kotlinDataTypeParser.parseDataType(field::class.simpleName!!, numericPref = NumericPreferencesVM.UseDefault())
        assertEquals(expectedValue, actualValue)

        expectedValue = "uint32"
        actualValue = kotlinDataTypeParser.parseDataType(field::class.simpleName!!, numericPref = NumericPreferencesVM.UseUInt())
        assertEquals(expectedValue, actualValue)

        expectedValue = "sint32"
        actualValue = kotlinDataTypeParser.parseDataType(field::class.simpleName!!, numericPref = NumericPreferencesVM.UseSInt())
        assertEquals(expectedValue, actualValue)

        expectedValue = "fixed32"
        actualValue = kotlinDataTypeParser.parseDataType(field::class.simpleName!!, numericPref = NumericPreferencesVM.UseFixed())
        assertEquals(expectedValue, actualValue)

        expectedValue = "sfixed32"
        actualValue = kotlinDataTypeParser.parseDataType(field::class.simpleName!!, numericPref = NumericPreferencesVM.UseSFixed())
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun parseLongDataType() {
        val field: Long = 0L
        expectedValue = "int64"
        actualValue = kotlinDataTypeParser.parseDataType(field::class.simpleName!!, numericPref = NumericPreferencesVM.UseDefault())
        assertEquals(expectedValue, actualValue)

        expectedValue = "uint64"
        actualValue = kotlinDataTypeParser.parseDataType(field::class.simpleName!!, numericPref = NumericPreferencesVM.UseUInt())
        assertEquals(expectedValue, actualValue)

        expectedValue = "sint64"
        actualValue = kotlinDataTypeParser.parseDataType(field::class.simpleName!!, numericPref = NumericPreferencesVM.UseSInt())
        assertEquals(expectedValue, actualValue)

        expectedValue = "fixed64"
        actualValue = kotlinDataTypeParser.parseDataType(field::class.simpleName!!, numericPref = NumericPreferencesVM.UseFixed())
        assertEquals(expectedValue, actualValue)

        expectedValue = "sfixed64"
        actualValue = kotlinDataTypeParser.parseDataType(field::class.simpleName!!, numericPref = NumericPreferencesVM.UseSFixed())
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun parseFloatDataType() {
        val field: Float = 0.0f
        expectedValue = "float"
        actualValue = kotlinDataTypeParser.parseDataType(field::class.simpleName!!, numericPref = NumericPreferencesVM.UseDefault())
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun parseDoubleDataType() {
        val field: Double = 0.0
        expectedValue = "double"
        actualValue = kotlinDataTypeParser.parseDataType(field::class.simpleName!!, numericPref = NumericPreferencesVM.UseDefault())
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun parseCharDataType() {
        val field: Char = 'a'
        expectedValue = "string"
        actualValue = kotlinDataTypeParser.parseDataType(field::class.simpleName!!, numericPref = NumericPreferencesVM.UseDefault())
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun parseStringDataType() {
        val field: String = ""
        expectedValue = "string"
        actualValue = kotlinDataTypeParser.parseDataType(field::class.simpleName!!, numericPref = NumericPreferencesVM.UseDefault())
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun parseByteDataType() {
        val field: Byte = 0
        expectedValue = "bytes"
        actualValue = kotlinDataTypeParser.parseDataType(field::class.simpleName!!, numericPref = NumericPreferencesVM.UseDefault())
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun parseObjectDataType() {
        val field = DummyModel(0,"")
        expectedValue = "DummyModel"
        actualValue = kotlinDataTypeParser.parseDataType(field::class.simpleName!!, numericPref = NumericPreferencesVM.UseDefault())
        assertEquals(expectedValue, actualValue)
    }
}