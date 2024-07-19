package com.fnakhsan.toprotobufconverter.converter.parser

import com.fnakhsan.toprotobufconverter.converter.template.CommonTemplate.SEMICOLON
import com.fnakhsan.toprotobufconverter.converter.template.FieldOptionTemplate.DEPRECATED
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.BOOL
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.BYTES
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.DOUBLE
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.FIXED32
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.FIXED64
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.FLOAT
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.INT32
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.INT64
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.OPTIONAL
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.REPEATED
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.SFIXED32
import com.fnakhsan.toprotobufconverter.converter.template.Keyword.SFIXED64
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
            it.type.apply {
                if (this.presentableText.contains("[]")) {
                    fieldLabels.add(REPEATED)
                    parseDataType(
                        fieldTypeName = this.presentableText.removeSuffix("[]"),
                        numericPref = numericPref
                    )
                } else if (this.presentableText.contains("List")) {
                    fieldLabels.add(REPEATED)
                    parseDataType(
                        fieldTypeName = this.presentableText.removePrefix("List<").removeSuffix(">")
                            .replace("java.lang.", ""), numericPref = numericPref
                    )
                } else if (this.presentableText.contains("Set")) {
                    fieldLabels.add(REPEATED)
                    parseDataType(
                        fieldTypeName = this.presentableText.removePrefix("Set<").removeSuffix(">")
                            .replace("java.lang.", ""), numericPref = numericPref
                    )
                } else if (this.presentableText.contains("Map")) {
                    val mapValue = this.presentableText.removePrefix("Map<").removeSuffix(">").replace(" ", "").split(",")
                    parseDataType(fieldTypeName = mapValue.first(), numericPref = numericPref)
                    val key = fieldType
                    parseDataType(fieldTypeName = mapValue.last(), numericPref = numericPref)
                    val value = fieldType
                    fieldType = "map<$key, $value>"
                } else {
                    parseDataType(fieldTypeName = this.presentableText, numericPref = numericPref)
                }
            }
        }
        if (fieldLabels.isNotEmpty()) {
            prefix.append(fieldLabels.joinToString(" ")).append(" ")
        }
        if (fieldOptions.isNotEmpty()) {
            suffix.append(" [").append(fieldOptions.joinToString(", ")).append("]")
        }
        return prefix.toString() + fieldType + " " + field.name + " = " + id.toString() + suffix.toString() + SEMICOLON
    }

    override fun parseDataType(fieldTypeName: String, numericPref: NumericPreferencesVM): String {
        fieldType = fieldTypeName.let {
            if (it.contains("boolean", ignoreCase = true)) BOOL else if (it.contains(
                    "short",
                    ignoreCase = true
                )
            ) parseNumericDataType(is64 = false, numericPref = numericPref) else if (it.contains(
                    "int",
                    ignoreCase = true
                )
            ) parseNumericDataType(is64 = false, numericPref = numericPref) else if (it.contains(
                    "integer",
                    ignoreCase = true
                )
            ) parseNumericDataType(is64 = false, numericPref = numericPref) else if (it.contains(
                    "long",
                    ignoreCase = true
                )
            ) parseNumericDataType(is64 = true, numericPref = numericPref) else if (it.contains(
                    "float",
                    ignoreCase = true
                )
            ) FLOAT else if (it.contains(
                    "double",
                    ignoreCase = true
                )
            ) DOUBLE else if (it.contains(
                    "char",
                    ignoreCase = true
                )
            ) STRING else if (it.contains(
                    "string",
                    ignoreCase = true
                )
            ) STRING else if (it.contains(
                    "byte",
                    ignoreCase = true
                )
            ) BYTES else fieldTypeName
        }
        return fieldType
    }

    override fun parseNumericDataType(is64: Boolean, numericPref: NumericPreferencesVM): String =
        when (numericPref.propertyName) {
            UINT -> if (is64) UINT64 else UINT32
            SINT -> if (is64) SINT64 else SINT32
            FIXED -> if (is64) FIXED64 else FIXED32
            SFIXED -> if (is64) SFIXED64 else SFIXED32
            else -> if (is64) INT64 else INT32
        }
}