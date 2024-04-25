package com.fnakhsan.toprotobufconverter.core.models

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory

data class ConversionModel (
    val rewriteClasses: Boolean,
//    val useKotlin: Boolean,
    val versionEnum: VersionVW,
    val preferenceEnum: NumericPreferencesVM,
    val rootFileName: String,
    val content: String?
)

data class ProjectModel(
    val directory: PsiDirectory,
    val packageName: String?,
    val virtualFolder: VirtualFile,
    val project: Project
)

data class FieldModel(
    val classType: String? = null,
    val fieldName: String? = null,
    val fieldNameFormatted: String? = null,
    val option: String? = null,
    val visibility: Visibility = Visibility.NONE
)

enum class Visibility(val value: String) {
    NONE(""), PRIVATE("private")
}