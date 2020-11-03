package com.unilever.julia.ui.inovacao.detalhe

import com.unilever.julia.data.model.InovacaoProjetoModel
import com.unilever.julia.ui.base.BasePresenterImpl
import javax.inject.Inject

class InovacaoDetalhePresenterImpl<V : InovacaoDetalheView, I : InovacaoDetalheInteractor>
@Inject constructor(mView: V, mInteractor: I) : BasePresenterImpl<V, I>(mView, mInteractor), InovacaoDetalhePresenter<V, I> {

    override fun sendEventInnovation(model : InovacaoProjetoModel) {
        getInteractor().firebaseAnalytics().addEventInnovationDetails(model)
    }

    override fun sendEventTradestory(model : InovacaoProjetoModel) {
        getInteractor().firebaseAnalytics().addEventInnovationTradestory(model)
    }
}