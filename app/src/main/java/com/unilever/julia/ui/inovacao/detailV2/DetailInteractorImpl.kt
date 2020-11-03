package com.unilever.julia.ui.inovacao.detailV2

import com.google.gson.Gson
import com.unilever.julia.ui.base.*

import com.unilever.julia.data.*
import com.unilever.julia.data.model.Conversations
import com.unilever.julia.data.model.inovacaoDetalhe.InovacaoDetalhe
import io.reactivex.Observable
import io.reactivex.functions.Function

import javax.inject.Inject

class DetailInteractorImpl
@Inject constructor(mDataManager: DataManager) : BaseInteractorImpl(mDataManager), DetailInteractor {

    private val mGson = Gson()

    override fun getDetail(sessionCode: String, code: String, identifier: String): Observable<InovacaoDetalhe> {
        return dataManager().juliaApi().getDetailInnovationProject(sessionCode, code, identifier)
            .flatMap(Function<Conversations, Observable<InovacaoDetalhe>> { conversations ->
                val model = mGson.fromJson(conversations.getAnswer().technicalText, InovacaoDetalhe::class.java)
                return@Function Observable.just(model)
            })
    }

    override fun putDownloadTradestory(sessionCode: String, code: String, identifier: String): Observable<InovacaoDetalhe> {
        return dataManager().juliaApi().sendDownloadTradeStory(sessionCode, "05_INOVACAO_DOWNLOAD", identifier)
            .flatMap(Function<Conversations, Observable<InovacaoDetalhe>> { conversations ->
                return@Function getDetail(sessionCode, code, identifier)
            })
    }
}