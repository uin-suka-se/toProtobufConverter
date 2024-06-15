package com.fnakhsan.toprotobufconverter.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.fnakhsan.toprotobufconverter.di.PluginApplication

class GenerateProtobufAction : AnAction() {
    override fun actionPerformed(
        event: AnActionEvent
    ) = PluginApplication.actionPerformed(event)
}
