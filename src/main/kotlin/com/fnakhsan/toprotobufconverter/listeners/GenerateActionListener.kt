package com.fnakhsan.toprotobufconverter.listeners

//import com.fnakhsan.toprotobufconverter.converter.utils.MessageConversionHelper
import com.fnakhsan.toprotobufconverter.converter.utils.MessageConversionHelper
import com.fnakhsan.toprotobufconverter.core.PluginException
import com.fnakhsan.toprotobufconverter.core.delegates.MessageDelegate
import com.fnakhsan.toprotobufconverter.presentation.ViewModelMapper
import com.fnakhsan.toprotobufconverter.presentation.form.ConverterForm
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

internal class GenerateActionListener(
    private val converterForm: ConverterForm,
    private val eventListener: GuiFormEventListener,
    private val messageDelegate: MessageDelegate,
    private val messageConversionHelper: MessageConversionHelper,
    private val viewModelMapper: ViewModelMapper
) : ActionListener {

    override fun actionPerformed(actionEvent: ActionEvent) = try {
        messageConversionHelper.validateFileName(converterForm.tfFileName.text)
//        messageConversionHelper.validateClassContent(converterForm.kotlinTextArea.text)
        eventListener.onDataObtained(viewModelMapper.map(converterForm))
    } catch (exception: PluginException) {
        messageDelegate.onPluginExceptionHandled(exception)
    }
}
