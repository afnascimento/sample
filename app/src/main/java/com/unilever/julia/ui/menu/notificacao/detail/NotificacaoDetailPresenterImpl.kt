package com.unilever.julia.ui.menu.notificacao.detail

import com.unilever.julia.data.model.notificacao.Notification
import com.unilever.julia.data.model.notificacao.NotificationRead
import com.unilever.julia.data.model.notificacao.holder.AttachedViewType
import com.unilever.julia.data.model.notificacao.holder.HolderContentTop
import com.unilever.julia.firebase.parser.ActionCodeNotification
import com.unilever.julia.firebase.parser.FirebaseNotification
import com.unilever.julia.ui.base.BasePresenterImpl
import com.unilever.julia.components.LoadView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NotificacaoDetailPresenterImpl<V : NotificacaoDetailView, I : NotificacaoDetailInteractor>
@Inject constructor(mView: V, mInteractor: I) : BasePresenterImpl<V, I>(mView, mInteractor), NotificacaoDetailPresenter<V, I> {

    private var mFirebaseNotification : FirebaseNotification? = null

    override fun initView(sessionCode: String?, notification: Notification?, firebaseNotification : FirebaseNotification?) {
        if (firebaseNotification != null) {
            mFirebaseNotification = firebaseNotification
            val action : ActionCodeNotification = firebaseNotification as ActionCodeNotification
            initViewByPushNotification(sessionCode ?: "", action.getId())
        } else if (notification != null) {
            initViewByScreenNotification(sessionCode ?: "", notification)
        }
    }

    override fun onBackPressed() {
        if (mFirebaseNotification != null) {
            getView().redirectChatMainActivity()
            return
        }
        if (mNotificationRead != null) {
            handlerBackByScreenNotification(mNotificationRead!!)
            return
        }
    }

    private fun handlerBackByScreenNotification(notificationRead : NotificationRead) {
        if (notificationRead.isUpdateRead) {
            getView().setResultOK(notificationRead.notification)
        } else {
            getView().setResultCancelled()
        }
    }

    private var mNotificationRead : NotificationRead? = null

    override fun initViewByPushNotification(sessionCode: String, id: String) {
        getView().addDisposable(
            getInteractor().getNotificationDetail(sessionCode, id)
                .flatMap { notification ->
                    return@flatMap getInteractor().sendNotificationRead(sessionCode, notification)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    getView().showLoadView()
                }
                .subscribeWith(object : DisposableObserver<NotificationRead>() {
                    override fun onNext(value: NotificationRead) {
                        mNotificationRead = value
                        getView().addItems(getAttachedItems(value.notification))
                    }

                    override fun onComplete() {
                        getView().hideLoadView()
                    }

                    override fun onError(e: Throwable) {
                        getView().hideLoadView()
                        getView().onErrorHandler(e, LoadView.OnClickListener { initViewByPushNotification(sessionCode, id) })
                    }
                })
        )
    }

    override fun initViewByScreenNotification(sessionCode: String, notification: Notification) {
        getView().addDisposable(
            getInteractor().sendNotificationRead(sessionCode, notification)
                .delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    getView().showLoadView()
                }
                .subscribeWith(object : DisposableObserver<NotificationRead>() {
                    override fun onNext(value: NotificationRead) {
                        mNotificationRead = value
                        getView().addItems(getAttachedItems(value.notification))
                    }

                    override fun onComplete() {
                        getView().hideLoadView()
                    }

                    override fun onError(e: Throwable) {
                        getView().hideLoadView()
                        getView().onErrorHandler(
                            e, LoadView.OnClickListener {
                                initViewByScreenNotification(sessionCode, notification)
                            }
                        )
                    }
                })
        )
    }

    override fun getAttachedItems(notification: Notification): List<AttachedViewType> {
        val list = arrayListOf<AttachedViewType>()

        val contentTop = HolderContentTop()
        contentTop.date = notification.date
        contentTop.title = notification.getTitleDetail()
        contentTop.description = notification.getDescriptionDetail()
        list.add(contentTop)

        if (!notification.detail?.attached.isNullOrEmpty()) {
            for (it in notification.detail?.attached!!) {
                list.add(it)
            }
        }

        return list
    }
}