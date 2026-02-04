package com.cdd

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.service

data class IcpSettingsState(
    var autoCalculateOnSave: Boolean = false
)

@Service(Service.Level.APP)
@State(name = "cddIcpSettings", storages = [Storage("cddIcp.xml")])
class IcpSettingsService : PersistentStateComponent<IcpSettingsState> {
    private var state = IcpSettingsState()

    override fun getState(): IcpSettingsState = state

    override fun loadState(state: IcpSettingsState) {
        this.state = state
    }

    fun isAutoCalculateOnSave(): Boolean = state.autoCalculateOnSave

    fun setAutoCalculateOnSave(enabled: Boolean) {
        state.autoCalculateOnSave = enabled
    }

    companion object {
        fun getInstance(): IcpSettingsService = service()
    }
}
