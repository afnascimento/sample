package com.unilever.julia.ui.inovacao.projetos

import com.unilever.julia.data.model.InovacaoProjetoModel
import com.unilever.julia.ui.base.BasePresenterImpl
import com.unilever.julia.components.LoadView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class InovacaoProjetosPresenterImpl<V : InovacaoProjetosView, I : InovacaoProjetosInteractor>
@Inject constructor(mView: V, mInteractor: I) : BasePresenterImpl<V, I>(mView, mInteractor), InovacaoProjetosPresenter<V, I> {

    override fun getProjetosInovacoes(sessionCode: String, code: String, marcaId: String, categoriaId: String, commodityId: String) {
        getView().addDisposable(
            getInteractor().getListaInovacoes(sessionCode, code, marcaId, categoriaId, commodityId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    getView().showLoadView()
                }
                .subscribeWith(object : DisposableObserver<MutableList<InovacaoProjetoModel>>() {
                    override fun onNext(value: MutableList<InovacaoProjetoModel>) {
                        getView().addInovacoes(value)
                    }

                    override fun onComplete() {
                        getView().hideLoadView()
                    }

                    override fun onError(e: Throwable) {
                        getView().onErrorHandler(e, LoadView.OnClickListener {
                            getProjetosInovacoes(sessionCode, code, marcaId, categoriaId, commodityId)
                        })
                    }
                })
        )
    }
}