package com.unilever.julia.ui.menu.notificacao.detail

import com.unilever.julia.data.model.notificacao.Notification
import com.unilever.julia.data.model.notificacao.NotificationRead
import com.unilever.julia.ui.base.BaseInteractor
import io.reactivex.Observable

interface NotificacaoDetailInteractor: BaseInteractor {
    fun sendNotificationRead(
        sessionCode: String,
        notification: Notification
    ): Observable<NotificationRead>

    fun getNotificationDetail(
        sessionCode: String,
        id: String
    ): Observable<Notification>
}