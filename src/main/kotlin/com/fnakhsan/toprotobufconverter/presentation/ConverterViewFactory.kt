package com.fnakhsan.toprotobufconverter.presentation

import com.fnakhsan.toprotobufconverter.converter.utils.MessageConversionHelper
import com.fnakhsan.toprotobufconverter.core.delegates.MessageDelegate
import com.fnakhsan.toprotobufconverter.core.models.ProjectModel
import com.fnakhsan.toprotobufconverter.core.models.SourceVM
import com.fnakhsan.toprotobufconverter.listeners.GenerateActionListener
import com.fnakhsan.toprotobufconverter.listeners.GuiFormEventListener
import com.fnakhsan.toprotobufconverter.presentation.form.ConverterForm
import com.fnakhsan.toprotobufconverter.presentation.form.KotlinConverterForm
import com.intellij.openapi.ui.DialogBuilder
import com.intellij.openapi.vfs.VfsUtil

internal class ConverterViewFactory(
    private val messageDelegate: MessageDelegate,
    private val messageConversionHelper: MessageConversionHelper,
    private val viewModelMapper: ViewModelMapper,
    private val propertiesFactory: PropertiesFactory,
    private val viewStateManager: ViewStateManager,
) {
    private lateinit var converterForm: ConverterForm

    fun bindView(builder: DialogBuilder, eventListener: GuiFormEventListener, projectModel: ProjectModel) {
        when (projectModel.sourceLanguage) {
            SourceVM.KOTLIN -> converterForm =
                KotlinConverterForm(propertiesFactory = propertiesFactory, viewStateManager = viewStateManager)
        }
        val actionListener = GenerateActionListener(
            converterForm = converterForm,
            eventListener = eventListener,
            messageDelegate = messageDelegate,
            messageConversionHelper = messageConversionHelper,
            viewModelMapper = viewModelMapper
        )
        with(converterForm) {
            bindView(this)
            converterForm.content = VfsUtil.loadText(projectModel.virtualFile)
            btnConvert.addActionListener(actionListener)
            builder.setCenterPanel(panelRoot)
        }
        builder.apply {
            setTitle(PLUGIN_TITLE)
            removeAllActions()
            show()
        }
    }
}

private const val PLUGIN_TITLE = "toProtobufConverter"