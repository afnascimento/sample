package com.unilever.julia.ui.menu.notificacao.detail

import com.unilever.julia.data.model.notificacao.Notification
import com.unilever.julia.data.model.notificacao.holder.AttachedViewType
import com.unilever.julia.ui.base.BaseView

interface NotificacaoDetailView : BaseView {
    fun addItems(items: List<AttachedViewType>)
    fun redirectChatMainActivity()
    fun setResultCancelled()
    fun setResultOK(notification: Notification)
}