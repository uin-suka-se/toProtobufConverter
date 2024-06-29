package com.fnakhsan.toprotobufconverter.converter.parser

import com.fnakhsan.toprotobufconverter.converter.template.FieldOptionTemplate.DEPRECATED
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.BYTES
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.DOUBLE
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.FIXED32
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.FIXED64
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.FLOAT
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.INT32
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.INT64
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.OPTIONAL
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.REPEATED
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.SINT32
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.SINT64
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.STRING
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.UINT32
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.UINT64
import com.fnakhsan.toprotobufconverter.core.models.NumericPreferencesVM
import com.fnakhsan.toprotobufconverter.core.models.NumericPreferencesVM.Companion.FIXED
import com.fnakhsan.toprotobufconverter.core.models.NumericPreferencesVM.Companion.SFIXED
import com.fnakhsan.toprotobufconverter.core.models.NumericPreferencesVM.Companion.SINT
import com.fnakhsan.toprotobufconverter.core.models.NumericPreferencesVM.Companion.UINT
import com.intellij.psi.PsiField
import org.jetbrains.kotlin.asJava.elements.KtLightField
import org.jetbrains.kotlin.j2k.ast.declarationIdentifier


class KotlinDataTypeParser : DataTypeParser {
    private var fieldLabels = mutableListOf<String>()
    private var fieldOptions = mutableListOf<String>()
    private lateinit var fieldType: String
    override fun parseField(id: Int, field: PsiField, numericPref: NumericPreferencesVM): String {
        val prefix = StringBuilder()
        val suffix = StringBuilder()
        (field as KtLightField).also {
            if (it.declarationIdentifier().isNullable) {
                fieldLabels.add(OPTIONAL)
            }
            if (it.isDeprecated) {
                fieldOptions.add(String().format(DEPRECATED, true))
            }
            if (it.children.size > 1) {
                println("list")
                fieldLabels.add(REPEATED)
                parseDataType(field = it, numericPref = numericPref)
            } else {
                println("object")
                parseDataType(field = it, numericPref = numericPref)
            }
        }
        if (fieldLabels.isNotEmpty()) {
            prefix.append(fieldLabels.joinToString(" ")).append(" ")
        }
        if (fieldOptions.isNotEmpty()) {
            suffix.append(" [").append(fieldOptions.joinToString(", ")).append("]")
        }
        return prefix.toString() + fieldType + " " + field.name + " = " + id.toString() + suffix.toString()
    }

    override fun parseDataType(field: PsiField, numericPref: NumericPreferencesVM) {
        println("typeName ${field.type.presentableText}")
        fieldType = when (field.type.presentableText) {
            "short" -> parseNumericDataType(is64 = false, numericPref = numericPref)
            "int" -> parseNumericDataType(is64 = false, numericPref = numericPref)
            "long" -> parseNumericDataType(is64 = true, numericPref = numericPref)
            "float" -> FLOAT
            "double" -> DOUBLE
            "char" -> STRING
            "String" -> STRING
            "byte" -> BYTES
            else -> field.type.presentableText
        }
    }

    override fun parseNumericDataType(is64: Boolean, numericPref: NumericPreferencesVM): String =
        when (numericPref.propertyName) {
            UINT -> if (is64) UINT64 else UINT32
            SINT -> if (is64) SINT64 else SINT32
            FIXED -> if (is64) FIXED64 else FIXED32
            SFIXED -> if (is64) FIXED64 else FIXED32
            else -> if (is64) INT64 else INT32
        }
}