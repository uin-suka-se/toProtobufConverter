package com.fnakhsan.toprotobufconverter.converter.filewriter

import com.fnakhsan.toprotobufconverter.core.models.ConversionModel
import com.fnakhsan.toprotobufconverter.core.models.ProjectModel
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.fileTypes.UserBinaryFileType
import com.intellij.psi.PsiFileFactory

internal class FileWriter {

    fun writeToFile(
        messageFieldBody: String,
        fileName: String,
        projectModel: ProjectModel,
        conversionModel: ConversionModel
    ) {
        val factory = PsiFileFactory.getInstance(projectModel.project)
        val fileNew = factory.createFileFromText(
            fileName,
            UserBinaryFileType.INSTANCE,
            messageFieldBody
        )
        WriteCommandAction.runWriteCommandAction(projectModel.project) {
            val existingFile = projectModel.directory.findFile(fileName)
            if (existingFile == null) {
                projectModel.directory.add(fileNew)
            } else if (conversionModel.rewriteClasses) {
                existingFile.delete()
                projectModel.directory.add(fileNew)
            }
        }
    }
}
