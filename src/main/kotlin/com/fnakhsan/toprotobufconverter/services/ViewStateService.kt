package com.fnakhsan.toprotobufconverter.services

import com.fnakhsan.toprotobufconverter.core.models.ConversionModel
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.util.xmlb.XmlSerializerUtil

// https://plugins.jetbrains.com/docs/intellij/persisting-state-of-components.html
@Service(Service.Level.APP)
@State(
    name = "AppState"
)
internal class ViewStateService : PersistentStateComponent<PluginState> {
    private var state = PluginState()

    override fun getState() = state

    // mempertahankan state
    fun persistModel(model: ConversionModel) {
        this.state = PluginState(model)
    }

    override fun loadState(state: PluginState) {
        XmlSerializerUtil.copyBean(state, this)
    }
}

data class PluginState(
    val model: ConversionModel? = null
)
