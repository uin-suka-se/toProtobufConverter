package com.fnakhsan.toprotobufconverter.listeners

import com.fnakhsan.toprotobufconverter.converter.utils.MessageConversionHelper
import com.fnakhsan.toprotobufconverter.core.PluginException
import com.fnakhsan.toprotobufconverter.core.delegates.MessageDelegate
import com.fnakhsan.toprotobufconverter.presentation.ViewModelMapper
import com.fnakhsan.toprotobufconverter.presentation.form.ConverterForm
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.Test
import java.awt.event.ActionEvent
import kotlin.test.BeforeTest

internal class GenerateActionListenerTest {

    @RelaxedMockK
    lateinit var actionEvent: ActionEvent

    @RelaxedMockK
    lateinit var messageDelegate: MessageDelegate

    @RelaxedMockK
    lateinit var messageConversionHelper: MessageConversionHelper

    @RelaxedMockK
    lateinit var exception: PluginException

    @InjectMockKs
    lateinit var listener: GenerateActionListener

    @RelaxedMockK
    lateinit var converterForm: ConverterForm

    @RelaxedMockK
    lateinit var eventListener: GuiFormEventListener

    @RelaxedMockK
    lateinit var viewModelMapper: ViewModelMapper

    @BeforeTest
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

    @Test
    fun onActionHandled_withError() {
        every { messageConversionHelper.validateFileName(any()) } throws exception
        listener.actionPerformed(actionEvent)
        verify { messageDelegate.onPluginExceptionHandled(exception) }
    }
}