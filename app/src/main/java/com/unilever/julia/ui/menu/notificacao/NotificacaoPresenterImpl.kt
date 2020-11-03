package com.unilever.julia.ui.menu.notificacao

import com.unilever.julia.R
import com.unilever.julia.data.model.notificacao.Notification
import com.unilever.julia.data.model.notificacao.Notifications
import com.unilever.julia.data.model.notificacao.holder.NotificationViewType
import com.unilever.julia.data.model.notificacao.holder.TitleViewType
import com.unilever.julia.exception.EmptyDataException
import com.unilever.julia.ui.base.BasePresenterImpl
import com.unilever.julia.components.LoadView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NotificacaoPresenterImpl<V : NotificacaoView, I : NotificacaoInteractor>
@Inject constructor(mView: V, mInteractor: I) : BasePresenterImpl<V, I>(mView, mInteractor), NotificacaoPresenter<V, I> {

    private var mDataSet : List<NotificationViewType> = emptyList()

    override fun getNotificacoes(sessionCode: String) {
        getView().addDisposable(
            getInteractor().getNotifications(sessionCode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    getView().showLoadView()
                }
                .subscribeWith(object : DisposableObserver<Notifications>() {
                    override fun onNext(value: Notifications) {
                        mDataSet = getItemsSorted(value)
                        getView().addItems(mDataSet)
                    }

                    override fun onComplete() {
                        getView().hideLoadView()
                    }

                    override fun onError(e: Throwable) {
                        getView().hideLoadView()
                        if (!isThrowableEmptyDataException(e)) {
                            getView().onErrorHandler(e, LoadView.OnClickListener { getNotificacoes(sessionCode) })
                        }
                    }
                })
        )
    }

    private fun isThrowableEmptyDataException(e: Throwable): Boolean {
        if (e is EmptyDataException) {
            //e.title = getView().getTextString(R.string.opa)
            //e.description = getView().getTextString(R.string.notificacao_empty)
            getView().showLoadViewEmpty(
                getView().getTextString(R.string.opa),
                getView().getTextString(R.string.notificacao_empty)
            )
            return true
        }
        return false
    }

    override fun redirectItemNotification(item: Notification, position: Int) {
        getView().redirectItemNotification(item)
    }

    override fun updateNotificationRead(notification : Notification) {
        getView().addDisposable(
            Observable.just(getItemsSorted(notification, mDataSet))
                .delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    getView().showLoadView()
                }
                .subscribeWith(object : DisposableObserver<List<NotificationViewType>>() {
                    override fun onNext(value: List<NotificationViewType>) {
                        mDataSet = value
                        getView().addItems(mDataSet)
                    }

                    override fun onComplete() {
                        getView().hideLoadView()
                    }

                    override fun onError(e: Throwable) {
                        getView().hideLoadView()
                        if (!isThrowableEmptyDataException(e)) {
                            getView().onErrorHandler(e, LoadView.OnClickListener { updateNotificationRead(notification) })
                        }
                    }
                })
        )
    }

    override fun getItemsSorted(notification : Notifications): List<NotificationViewType> {
        return getItemsSorted(notification.notifications ?: arrayListOf())
    }

    override fun getItemsSorted(notification : Notification, items: List<NotificationViewType>): List<NotificationViewType> {
        val list = arrayListOf<Notification>()
        for (it in items) {
            if (it is Notification &&  it.id != notification.id) {
                list.add(it)
            }
        }
        list.add(notification)

        return getItemsSorted(list)
    }

    override fun getItemsSorted(notifications: List<Notification>): List<NotificationViewType> {
        val sortedByDate = notifications.sortedByDescending { it.date }
        val groupBy = sortedByDate.groupBy { it.read }

        val reads : List<Notification> = groupBy[true] ?: arrayListOf()
        val notReads : List<Notification> = groupBy[false] ?: arrayListOf()

        // transform to ids not reads
        val idsNotReads = mutableListOf<String>()
        notReads.mapTo(idsNotReads, { it.getIdString() })
        getInteractor().savePushMessageNotReads(idsNotReads.toSet())

        val response = arrayListOf<NotificationViewType>()
        if (notReads.isNotEmpty()) {
            response.add(TitleViewType(getView().getTextString(R.string.novidades)))
            response.addAll(notReads)
        }
        if (reads.isNotEmpty()) {
            response.add(TitleViewType(getView().getTextString(R.string.novidades_lidas)))
            response.addAll(reads)
        }

        return response
    }
}