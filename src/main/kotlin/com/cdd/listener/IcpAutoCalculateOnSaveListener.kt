package com.cdd.listener

import com.cdd.settings.CDDSettingsService
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileDocumentManagerListener
import com.intellij.openapi.project.ProjectLocator

class IcpAutoCalculateOnSaveListener : FileDocumentManagerListener {
    override fun beforeAnyDocumentSaving(document: Document, explicit: Boolean) {
        if (!CDDSettingsService.getInstance().isAutoCalculateOnSave()) {
            return
        }
        val file = FileDocumentManager.getInstance().getFile(document) ?: return
        val extension = file.extension?.lowercase() ?: ""
        if (extension != "kt" && extension != "java") {
            return
        }

        val notification = NotificationGroupManager.getInstance()
            .getNotificationGroup(NOTIFICATION_GROUP_ID)
            .createNotification(
                "ICP auto-calculate triggered for ${file.name}.",
                NotificationType.INFORMATION
            )
        val project = ProjectLocator.getInstance().guessProjectForFile(file)
        if (project != null) {
            notification.notify(project)
        } else {
            Notifications.Bus.notify(notification)
        }
    }

    companion object {
        const val NOTIFICATION_GROUP_ID = "cddAutoCalculateOnSave"
    }
}
