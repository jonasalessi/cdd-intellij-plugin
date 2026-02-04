package com.cdd

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.service
import com.intellij.openapi.application.ApplicationManager
import com.intellij.util.messages.Topic

data class IcpSettingsState(
    var autoCalculateOnSave: Boolean = false
)

interface IcpSettingsListener {
    fun handleAutoCalculateOnSaveChanged(isEnabled: Boolean)

    companion object {
        val TOPIC = Topic.create(
            "icpSettingsChanged",
            IcpSettingsListener::class.java
        )
    }
}

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
        if (state.autoCalculateOnSave == enabled) {
            return
        }
        state.autoCalculateOnSave = enabled
        ApplicationManager.getApplication()
            .messageBus
            .syncPublisher(IcpSettingsListener.TOPIC)
            .handleAutoCalculateOnSaveChanged(enabled)
    }

    companion object {
        fun getInstance(): IcpSettingsService = service()
    }
}
