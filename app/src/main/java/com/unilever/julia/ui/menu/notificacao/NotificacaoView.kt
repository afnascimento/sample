package com.unilever.julia.ui.menu.notificacao

import com.unilever.julia.data.model.notificacao.Notification
import com.unilever.julia.data.model.notificacao.holder.NotificationViewType
import com.unilever.julia.ui.base.BaseView

interface NotificacaoView : BaseView {
    fun addItems(items: List<NotificationViewType>)
    fun redirectItemNotification(item: Notification)
}