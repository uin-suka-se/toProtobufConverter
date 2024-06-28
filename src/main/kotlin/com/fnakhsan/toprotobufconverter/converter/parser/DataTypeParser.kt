package com.fnakhsan.toprotobufconverter.converter.parser

import com.fnakhsan.toprotobufconverter.core.models.NumericPreferencesVM
import com.intellij.psi.PsiField

interface DataTypeParser {
    fun parseDataType(id: Int, field: PsiField, numericPref: NumericPreferencesVM): String
    fun parsePrimitiveDataType(field: PsiField, numericPref: NumericPreferencesVM)
    fun parseObjectDataType(field: PsiField)
    fun parseNumericDataType(is64: Boolean, numericPref: NumericPreferencesVM): String
}