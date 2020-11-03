package com.unilever.julia.ui.menu.notificacao.detail

import com.google.gson.Gson
import com.unilever.julia.data.DataManager
import com.unilever.julia.data.model.notificacao.Notification
import com.unilever.julia.data.model.notificacao.NotificationRead
import com.unilever.julia.ui.base.BaseInteractorImpl
import io.reactivex.Observable
import javax.inject.Inject

class NotificacaoDetailInteractorImpl
@Inject constructor(dataManager: DataManager) : BaseInteractorImpl(dataManager), NotificacaoDetailInteractor {

    private val mGson = Gson()

    override fun getNotificationDetail(sessionCode: String, id: String): Observable<Notification> {
        return dataManager().juliaApi().getNotificationDetail(sessionCode, id)
            .flatMap { conversations ->
                val response = mGson.fromJson<Notification>(
                    conversations.getAnswer().technicalText,
                    Notification::class.java
                )
                return@flatMap Observable.just(response)
            }
    }

    override fun sendNotificationRead(sessionCode: String, notification: Notification): Observable<NotificationRead> {
        if (notification.isRead()) {
            return Observable.just(NotificationRead(notification, false))
        } else {
            return dataManager().juliaApi().sendNotificationRead(sessionCode, notification.id.toString())
                .flatMap { conversations ->
                    if (conversations.getAnswer().isSuccess()) {
                        notification.read = true
                    }

                    return@flatMap Observable.just(NotificationRead(notification, notification.isRead()))
                }
        }
    }
}