package com.fnakhsan.toprotobufconverter.di

import com.intellij.openapi.actionSystem.AnActionEvent
import com.fnakhsan.toprotobufconverter.controllers.GenerateProtobufActionController
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

object PluginApplication : KoinComponent {
    init {
        GlobalContext.getOrNull() ?: startKoin {
            modules(
                appModule
            )
        }
    }

    private val controller: GenerateProtobufActionController by inject()

    fun actionPerformed(actionEvent: AnActionEvent) {
        controller.onActionHandled(actionEvent)
    }
}
