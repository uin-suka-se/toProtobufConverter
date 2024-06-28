package com.fnakhsan.toprotobufconverter.converter.parser

import com.fnakhsan.toprotobufconverter.converter.template.FieldOptionTemplate.DEPRECATED
import com.fnakhsan.toprotobufconverter.converter.template.FieldTemplate.FIELD_TEMPLATE
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
import org.jetbrains.kotlin.analysis.api.types.KtType
import org.jetbrains.kotlin.asJava.elements.KtLightField


class KotlinDataTypeParser : DataTypeParser {
    private var fieldLabels = mutableListOf<String>()
    private var fieldOptions = mutableListOf<String>()
    private lateinit var fieldType: String
    override fun parseDataType(id: Int, field: PsiField, numericPref: NumericPreferencesVM): String {
        val prefix = StringBuilder()
        val suffix = StringBuilder()
        (field as KtLightField).also {
            if ((it.type as KtType).nullability.isNullable) {
                fieldLabels.add(OPTIONAL)
            }
            if (it.isDeprecated) {
                fieldOptions.add(String().format(DEPRECATED, true))
            }
            it.type::class.apply {
                if (it.type.arrayDimensions > 1) {
                    fieldLabels.add(REPEATED)
                    if (this.java.isPrimitive) {
                        parsePrimitiveDataType(field = it, numericPref = numericPref)
                    } else {
                        fieldType = it.firstChild::class.java.typeName
                    }
                } else {
                    if (this.java.isPrimitive) {
                        parsePrimitiveDataType(field = it, numericPref = numericPref)
                    } else {
                        fieldType = this.java.typeName
                    }
                }
            }
        }
        if(fieldLabels.isNotEmpty()) {
            prefix.append(fieldLabels.joinToString(" ")).append(" ")
        }
        if (fieldOptions.isNotEmpty()) {
            suffix.append(" [").append(fieldOptions.joinToString(", ")).append("]")
        }
        return String().format(FIELD_TEMPLATE, prefix.toString(), fieldType, field.name, id, suffix.toString())
    }

    override fun parsePrimitiveDataType(field: PsiField, numericPref: NumericPreferencesVM) {
        field.type::class.apply {
            when (this.simpleName) {
                "Short" -> fieldType = parseNumericDataType(is64 = false, numericPref = numericPref)
                "Int" -> fieldType = parseNumericDataType(is64 = false, numericPref = numericPref)
                "Long" -> fieldType = parseNumericDataType(is64 = true, numericPref = numericPref)
                "Float" -> fieldType = FLOAT
                "Double" -> fieldType = DOUBLE
                "Char" -> fieldType = STRING
                "String" -> fieldType = STRING
                "Byte" -> fieldType = BYTES
            }
        }
    }

    override fun parseObjectDataType(field: PsiField) {
//        fieldType = field.getJavaOrKotlinMemberDescriptor()?.name.toString()
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