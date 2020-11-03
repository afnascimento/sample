package com.unilever.julia.ui.inovacao.tradestoryEvaluation

import com.unilever.julia.R
import com.unilever.julia.data.model.Conversations
import com.unilever.julia.data.model.inovacaoDetalhe.Resumo
import com.unilever.julia.ui.base.*
import com.unilever.julia.utils.MaterialDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject

class TradeStoryEvaluationPresenterImpl<V : TradeStoryEvaluationView, I : TradeStoryEvaluationInteractor>
@Inject constructor(mView: V, mInteractor: I) : BasePresenterImpl<V, I>(mView, mInteractor), TradeStoryEvaluationPresenter<V, I> {

    override fun sendEvaluationTradeStory(sessionCode: String,
                                          identifier: String,
                                          stars: Int,
                                          description: String) {
        if (stars == 0) {
            getView().showToast(R.string.tradestory_evaluation_empty)
            return
        }

        getView().addDisposable(
            getInteractor().sendEvaluationTradeStory(sessionCode, identifier, stars, description)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    getView().showProgressDialog()
                }
                .subscribeWith(object : DisposableObserver<Resumo.TradeStory>() {
                    override fun onNext(value: Resumo.TradeStory) {
                        getView().backSuccess(value)
                    }

                    override fun onComplete() {
                        getView().hideProgressDialog()
                    }

                    override fun onError(e: Throwable) {
                        getView().hideProgressDialog()
                        getView().onErrorHandlerDialog(e, MaterialDialog.OnClickListener { dialog ->
                            dialog.dismiss()
                        })
                    }
                })
        )
    }
}