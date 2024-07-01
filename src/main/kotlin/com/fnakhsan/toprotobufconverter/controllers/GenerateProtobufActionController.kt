package com.fnakhsan.toprotobufconverter.controllers

import com.fnakhsan.toprotobufconverter.converter.ConversionDelegate
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.DialogBuilder
import com.fnakhsan.toprotobufconverter.core.delegates.EnvironmentDelegate
import com.fnakhsan.toprotobufconverter.core.delegates.MessageDelegate
import com.fnakhsan.toprotobufconverter.core.PluginException
import com.fnakhsan.toprotobufconverter.core.models.ConversionModel
import com.fnakhsan.toprotobufconverter.listeners.GuiFormEventListener
import com.fnakhsan.toprotobufconverter.presentation.ConverterViewFactory
import com.fnakhsan.toprotobufconverter.services.ViewStateService

internal class GenerateProtobufActionController(
    private val environmentDelegate: EnvironmentDelegate,
    private val messageDelegate: MessageDelegate,
    private val converterViewFactory: ConverterViewFactory,
    private val conversionDelegate: ConversionDelegate,
    private val viewStateService: ViewStateService
) {

    fun onActionHandled(event: AnActionEvent) = try {
        proceed(event)
    } catch (exception: PluginException) {
        messageDelegate.onPluginExceptionHandled(exception)
    }

    private fun proceed(event: AnActionEvent) {
        val projectModel = environmentDelegate.obtainProjectModel(event)
        val dialogBuilder = DialogBuilder()
        val window = dialogBuilder.window
        projectModel.sourceLanguage
        converterViewFactory.bindView(
            dialogBuilder,
            object : GuiFormEventListener {
                override fun onDataObtained(model: ConversionModel) {
                    viewStateService.persistModel(model)
                    window.dispose()
                    conversionDelegate.runConversionTask(model, projectModel)
                }
            },
            projectModel
        )
    }
}
