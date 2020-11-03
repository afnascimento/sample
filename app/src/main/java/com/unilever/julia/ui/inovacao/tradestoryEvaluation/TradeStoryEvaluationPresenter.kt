package com.unilever.julia.ui.inovacao.tradestoryEvaluation

import com.unilever.julia.ui.base.*

interface TradeStoryEvaluationPresenter<V : TradeStoryEvaluationView, I : TradeStoryEvaluationInteractor> :
    BasePresenter<V, I> {

    fun sendEvaluationTradeStory(
        sessionCode: String,
        identifier: String,
        stars: Int,
        description: String
    )
}