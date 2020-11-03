package com.unilever.julia.ui.menu.notificacao.detail

import com.unilever.julia.data.model.notificacao.Notification
import com.unilever.julia.data.model.notificacao.holder.AttachedViewType
import com.unilever.julia.firebase.parser.FirebaseNotification
import com.unilever.julia.ui.base.BasePresenter

interface NotificacaoDetailPresenter<V: NotificacaoDetailView, I: NotificacaoDetailInteractor> : BasePresenter<V, I> {

    fun initView(sessionCode: String?, notification: Notification?, firebaseNotification : FirebaseNotification?)
    fun getAttachedItems(notification: Notification): List<AttachedViewType>
    fun onBackPressed()
    fun initViewByPushNotification(sessionCode: String, id: String)
    fun initViewByScreenNotification(sessionCode: String, notification: Notification)
}