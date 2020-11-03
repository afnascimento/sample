package com.unilever.julia.ui.menu.notificacao

import com.unilever.julia.data.model.notificacao.Notification
import com.unilever.julia.data.model.notificacao.Notifications
import com.unilever.julia.data.model.notificacao.holder.NotificationViewType
import com.unilever.julia.ui.base.BasePresenter

interface NotificacaoPresenter<V: NotificacaoView, I: NotificacaoInteractor> : BasePresenter<V, I> {
    fun getNotificacoes(sessionCode: String)
    fun redirectItemNotification(item: Notification, position: Int)
    fun updateNotificationRead(notification: Notification)
    fun getItemsSorted(notification: Notifications): List<NotificationViewType>
    fun getItemsSorted(
        notification: Notification,
        items: List<NotificationViewType>
    ): List<NotificationViewType>

    fun getItemsSorted(notifications: List<Notification>): List<NotificationViewType>
}