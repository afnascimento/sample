package com.unilever.julia.ui.inovacao.tradestoryEvaluation

import com.unilever.julia.data.model.inovacaoDetalhe.Resumo
import com.unilever.julia.ui.base.*

interface TradeStoryEvaluationView : BaseView {

    fun backSuccess(tradestory : Resumo.TradeStory)
}