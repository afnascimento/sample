package com.unilever.julia.ui.menu.notificacao

import com.unilever.julia.data.model.notificacao.Notifications
import com.unilever.julia.ui.base.BaseInteractor
import io.reactivex.Observable

interface NotificacaoInteractor: BaseInteractor {
    fun getNotifications(sessionCode: String): Observable<Notifications>
    fun savePushMessageNotReads(idsNotReads: Set<String>)
}