package com.fnakhsan.toprotobufconverter.controllers

import com.fnakhsan.toprotobufconverter.core.PluginException
import com.fnakhsan.toprotobufconverter.core.delegates.EnvironmentDelegate
import com.fnakhsan.toprotobufconverter.core.delegates.MessageDelegate
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