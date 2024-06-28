package com.fnakhsan.toprotobufconverter.converter.template

import com.fnakhsan.toprotobufconverter.core.models.NumericPreferencesVM
import com.intellij.psi.PsiField

interface ProtobufTemplateHelper {
    fun getVersion(): String
    fun getPackage(): String
    fun getImport(imports: List<String>): String
    fun getFileOption(): String
    fun getMessage(name: String, body: String): String
    fun getField(id: Int, field: PsiField, numericPref: NumericPreferencesVM): String
    fun getAllContent(): String
}