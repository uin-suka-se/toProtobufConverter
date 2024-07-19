package com.fnakhsan.toprotobufconverter.converter.parser

import com.fnakhsan.toprotobufconverter.core.models.NumericPreferencesVM
import com.intellij.psi.PsiField

interface DataTypeParser {
    fun parseField(id: Int, field: PsiField, numericPref: NumericPreferencesVM): String
    fun parseDataType(fieldTypeName: String, numericPref: NumericPreferencesVM): String
    fun parseNumericDataType(is64: Boolean, numericPref: NumericPreferencesVM): String
}