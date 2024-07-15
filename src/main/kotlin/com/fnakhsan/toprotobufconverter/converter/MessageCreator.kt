package com.fnakhsan.toprotobufconverter.converter

import com.fnakhsan.toprotobufconverter.converter.processor.KotlinToProtobufProcessor
import com.fnakhsan.toprotobufconverter.core.FileWriteException
import com.fnakhsan.toprotobufconverter.core.models.ConversionModel
import com.fnakhsan.toprotobufconverter.core.models.ProjectModel
import com.fnakhsan.toprotobufconverter.core.models.SourceVM
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.fileTypes.UserBinaryFileType
import com.intellij.psi.PsiFileFactory
import java.io.IOException


internal class MessageCreator {

    internal fun generateFiles(
        conversionModel: ConversionModel,
        projectModel: ProjectModel
    ) {
        try {
            projectModel.apply {
                val factory = PsiFileFactory.getInstance(project)
                val fileNew = factory.createFileFromText(
                    conversionModel.rootFileName.plus(".proto"),
                    UserBinaryFileType.INSTANCE,
                    generateContent(sourceLanguage, this, conversionModel)
                )

                this.psiFile.containingDirectory?.apply {
                    WriteCommandAction.runWriteCommandAction(project) {
                        val existingFile =
                            findFile(conversionModel.rootFileName)
                        if (existingFile == null) {
                            this.add(fileNew)
                        } else {
                            existingFile.delete()
                            this.add(fileNew)
                        }
                    }
                }
            }
        } catch (e: IOException) {
            throw FileWriteException(e.message)
        }
    }

    internal fun generateContent(
        sourceLanguage: String,
        projectModel: ProjectModel,
        conversionModel: ConversionModel
    ): String =
        when (sourceLanguage) {
            SourceVM.KOTLIN -> {
                KotlinToProtobufProcessor(
                    projectModel = projectModel,
                    conversionModel = conversionModel
                ).getAllContent()
            }

            else -> ""
        }

}
