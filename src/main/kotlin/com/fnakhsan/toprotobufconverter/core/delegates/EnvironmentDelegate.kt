package com.fnakhsan.toprotobufconverter.core.delegates

import com.fnakhsan.toprotobufconverter.core.SourceException
import com.fnakhsan.toprotobufconverter.core.models.ProjectModel
import com.fnakhsan.toprotobufconverter.core.models.SourceVM
import com.intellij.ide.projectView.ProjectView
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import org.jetbrains.kotlin.codegen.topLevelClassInternalName
import org.jetbrains.kotlin.psi.KtFile

interface EnvironmentDelegate {
    fun obtainProjectModel(event: AnActionEvent): ProjectModel
    fun refreshProject(projectModel: ProjectModel)
}

internal class EnvironmentDelegateImpl : EnvironmentDelegate {
    private lateinit var source: String
    private lateinit var packageName: String
    override fun obtainProjectModel(event: AnActionEvent): ProjectModel {
        val project = event.project as Project
        // Apa bedanya LangDataKeys.VIRTUAL_FILE sama CommonDataKeys.VIRTUAL_FILE ??
        val virtualFile = event.getData(CommonDataKeys.VIRTUAL_FILE) as VirtualFile
        val psiFile = event.getData(CommonDataKeys.PSI_FILE) as PsiFile

        when(virtualFile.extension) {
            "kt" -> {
                source = SourceVM.KOTLIN
                packageName = (psiFile as KtFile).packageFqName.topLevelClassInternalName()
            }
            else -> throw SourceException()
        }

        return ProjectModel(
            sourceLanguage = source,
            packageName = packageName,
            virtualFile = virtualFile,
            psiFile = psiFile,
            project = project
        )
    }

    override fun refreshProject(projectModel: ProjectModel) {
        ProjectView.getInstance(projectModel.project).refresh()
        projectModel.virtualFile.refresh(false, true)
    }
}