package com.unilever.julia.ui.inovacao.tradestoryEvaluation

import com.unilever.julia.data.model.inovacaoDetalhe.Resumo
import com.unilever.julia.ui.base.*
import io.reactivex.Observable

interface TradeStoryEvaluationInteractor : BaseInteractor {

    fun sendEvaluationTradeStory(
        sessionCode: String,
        identifier: String,
        stars: Int,
        description: String
    ): Observable<Resumo.TradeStory>
}