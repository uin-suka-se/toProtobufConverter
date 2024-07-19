package com.fnakhsan.toprotobufconverter.controllers

import com.fnakhsan.toprotobufconverter.converter.ConversionDelegate
import com.fnakhsan.toprotobufconverter.core.PluginException
import com.fnakhsan.toprotobufconverter.core.delegates.EnvironmentDelegate
import com.fnakhsan.toprotobufconverter.core.delegates.MessageDelegate
import com.fnakhsan.toprotobufconverter.core.models.ProjectModel
import com.fnakhsan.toprotobufconverter.presentation.ConverterViewFactory
import com.fnakhsan.toprotobufconverter.services.ViewStateService
import com.intellij.openapi.actionSystem.AnActionEvent
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlin.test.BeforeTest
import kotlin.test.Test

internal class GenerateProtobufActionControllerTest {

    @RelaxedMockK
    lateinit var environmentDelegate: EnvironmentDelegate

    @RelaxedMockK
    lateinit var messageDelegate: MessageDelegate

    @RelaxedMockK
    lateinit var event: AnActionEvent

    @InjectMockKs
    lateinit var generateProtobufActionController: GenerateProtobufActionController

    @RelaxedMockK
    lateinit var converterViewFactory: ConverterViewFactory

    @RelaxedMockK
    lateinit var conversionDelegate: ConversionDelegate

    @RelaxedMockK
    lateinit var projectModel: ProjectModel

    @RelaxedMockK
    lateinit var viewStateService: ViewStateService

    @BeforeTest
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

    @Test
    fun onActionHandled_withError() {
        val exception = PluginException("", "")
        every { environmentDelegate.obtainProjectModel(event) }.throws(exception)
        generateProtobufActionController.onActionHandled(event)
        verify { messageDelegate.onPluginExceptionHandled(exception) }
    }
}