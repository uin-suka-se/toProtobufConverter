package com.fnakhsan.toprotobufconverter.presentation

import com.fnakhsan.toprotobufconverter.converter.utils.MessageConversionHelper
import com.fnakhsan.toprotobufconverter.core.delegates.MessageDelegate
import com.fnakhsan.toprotobufconverter.listeners.GuiFormEventListener
import com.fnakhsan.toprotobufconverter.presentation.form.ConverterForm
import com.intellij.openapi.ui.DialogBuilder
import com.fnakhsan.toprotobufconverter.listeners.GenerateActionListener

internal class ConverterViewFactory(
    private val messageDelegate: MessageDelegate,
    private val messageConversionHelper: MessageConversionHelper,
    private val converterViewBinder: ConverterViewBinder,
    private val viewModelMapper: ViewModelMapper
) {

    fun bindView(builder: DialogBuilder, eventListener: GuiFormEventListener) {
        val converterForm = ConverterForm()
        val actionListener = GenerateActionListener(
            converterForm = converterForm,
            eventListener = eventListener,
            messageDelegate = messageDelegate,
            messageConversionHelper = messageConversionHelper,
            viewModelMapper = viewModelMapper
        )
        with(converterForm) {
            converterViewBinder.bindView(this)
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