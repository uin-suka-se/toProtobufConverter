package com.fnakhsan.toprotobufconverter.converter

import com.fnakhsan.toprotobufconverter.core.PluginException
import com.fnakhsan.toprotobufconverter.core.delegates.EnvironmentDelegate
import com.fnakhsan.toprotobufconverter.core.delegates.MessageDelegate
import com.fnakhsan.toprotobufconverter.core.models.ConversionModel
import com.fnakhsan.toprotobufconverter.core.models.ProjectModel


interface ConversionDelegate {
    fun runConversionTask(
        conversionModel: ConversionModel,
        projectModel: ProjectModel
    )
}

internal class ConversionDelegateImpl(
    private val classCreator: ClassCreator,
    private val environmentDelegate: EnvironmentDelegate,
    private val messageDelegate: MessageDelegate
) : ConversionDelegate {

    override fun runConversionTask(
        conversionModel: ConversionModel,
        projectModel: ProjectModel
    ) {
        try {
            classCreator.generateFiles(conversionModel, projectModel)
            messageDelegate.showSuccessMessage()
        } catch (e: PluginException) {
            messageDelegate.onPluginExceptionHandled(e)
        } finally {
            environmentDelegate.refreshProject(projectModel)
        }
    }
}
