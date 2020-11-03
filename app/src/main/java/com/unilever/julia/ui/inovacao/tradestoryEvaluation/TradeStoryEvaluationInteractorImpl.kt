package com.unilever.julia.ui.inovacao.tradestoryEvaluation

import com.google.gson.Gson
import com.unilever.julia.ui.base.*

import com.unilever.julia.data.*
import com.unilever.julia.data.model.Conversations
import com.unilever.julia.data.model.inovacaoDetalhe.Resumo
import io.reactivex.Observable
import org.apache.commons.lang3.StringUtils

import javax.inject.Inject

class TradeStoryEvaluationInteractorImpl
@Inject constructor(mDataManager: DataManager) : BaseInteractorImpl(mDataManager), TradeStoryEvaluationInteractor {

    private var mGson: Gson = Gson()

    override fun sendEvaluationTradeStory(sessionCode: String,
                                          identifier: String,
                                          stars: Int,
                                          description: String): Observable<Resumo.TradeStory> {
        return juliaUnileverApi().sendEvaluationTradeStory(sessionCode, "06_INOVACAO_AVALIACAO", identifier, stars, description)
            .flatMap { conversations: Conversations ->
                val tradestory = mGson.fromJson(
                    StringUtils.trimToEmpty(conversations.getAnswer().technicalText),
                    Resumo.TradeStory::class.java
                )
                return@flatMap Observable.just(tradestory)
            }
    }
}