package com.fnakhsan.toprotobufconverter.presentation

import com.fnakhsan.toprotobufconverter.converter.utils.ConversionHelper
import com.fnakhsan.toprotobufconverter.core.delegates.MessageDelegate
import com.fnakhsan.toprotobufconverter.presentation.form.ConverterForm
import com.intellij.openapi.ui.DialogBuilder

internal class ConverterViewFactory(
    private val messageDelegate: MessageDelegate,
    private val generatorViewBinder: GeneratorViewBinder,
    private val viewModelMapper: ViewModelMapper
) {

    fun bindView(builder: DialogBuilder, eventListener: GuiFormEventListener) {
        val converterForm = ConverterForm()
        val actionListener = GenerateActionListener(
            converterForm = converterForm,
            eventListener = eventListener,
            messageDelegate = messageDelegate,
            conversionHelper = ConversionHelper,
            viewModelMapper = viewModelMapper
        )
        with(converterForm) {
            generatorViewBinder.bindView(this)
            btnConvert.addActionListener(actionListener)
            builder.setCenterPanel(rootPanel)
        }
        builder.apply {
            setTitle(PLUGIN_TITLE)
            removeAllActions()
            show()
        }
    }
}

private const val PLUGIN_TITLE = "toProtobufConverter"