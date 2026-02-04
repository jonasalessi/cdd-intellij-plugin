package com.cdd

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.StatusBarWidget
import com.intellij.openapi.wm.StatusBarWidgetFactory

class IcpStatusBarWidgetFactory : StatusBarWidgetFactory {
    override fun getId(): String = IcpStatusBarWidget.ID

    override fun getDisplayName(): String = "ICP"

    override fun isAvailable(project: Project): Boolean {
        return !project.isDisposed && !project.isDefault
    }

    override fun createWidget(project: Project): StatusBarWidget = IcpStatusBarWidget(project)

    override fun disposeWidget(widget: StatusBarWidget) {
        widget.dispose()
    }

    override fun canBeEnabledOn(statusBar: com.intellij.openapi.wm.StatusBar): Boolean = true
}
