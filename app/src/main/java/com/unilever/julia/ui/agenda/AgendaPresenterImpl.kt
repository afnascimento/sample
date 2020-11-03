package com.unilever.julia.ui.agenda

import com.unilever.julia.data.enums.AgendaStatusEnum
import com.unilever.julia.data.model.AgendaModel
import com.unilever.julia.data.model.Conversations
import com.unilever.julia.ui.adapter.ChatMainAdapter
import com.unilever.julia.ui.base.BasePresenterImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AgendaPresenterImpl<V : AgendaView, I : AgendaInteractor>
@Inject constructor(mView: V, mInteractor: I) : BasePresenterImpl<V, I>(mView, mInteractor), AgendaPresenter<V, I> {

    override fun agendaMudarStatusEvento(sessionCode: String, status: AgendaStatusEnum, item: AgendaModel.Item) {
        getView().addDisposable(
            getInteractor().sendAgendaStatusChange(sessionCode, status, item)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    getView().showProgressDialog()
                }
                .subscribeWith(object : DisposableObserver<Conversations>() {
                    override fun onNext(conversations: Conversations) {
                    }

                    override fun onComplete() {
                        getView().hideProgressDialog()
                        getView().eventoFinalizado()
                    }

                    override fun onError(e: Throwable) {
                        getView().hideProgressDialog()
                    }
                })
        )
    }
}