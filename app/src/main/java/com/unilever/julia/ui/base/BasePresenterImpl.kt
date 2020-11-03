package com.unilever.julia.ui.base

import com.unilever.julia.data.model.AgendaOutlookExcluir
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by andre.nascimento on 27/12/2018.
 */
open class BasePresenterImpl<V : BaseView, I : BaseInteractor>
@Inject constructor(private val mView: V, private val mInteractor: I) : BasePresenter<V, I> {

    fun getView(): V {
        return mView
    }

    fun getInteractor(): I {
        return mInteractor
    }

    fun enviarExclusaoAgenda(idReuniao: String, sessionCode: String, onSubscribe: IOnSubscribe<String>) {
        val send = AgendaOutlookExcluir()
        send.code = "26_AGENDA_OUTLOOK_EXCLUIR"
        send.context.id = idReuniao

        getView().addDisposable(
            getInteractor().enviarExclusaoAgenda(sessionCode, send)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    onSubscribe.doOnSubscribe()
                }
                .subscribeWith(object : DisposableObserver<String>() {
                    override fun onNext(text: String) {
                        onSubscribe.onNext(text)
                    }

                    override fun onComplete() {
                        onSubscribe.onComplete()
                    }

                    override fun onError(e: Throwable) {
                        onSubscribe.onError(e)
                    }
                })
        )
    }
}