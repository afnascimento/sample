package com.unilever.julia.ui.inovacao.projetos

import javax.inject.Inject

import com.unilever.julia.data.DataManager
import com.unilever.julia.data.model.Answer
import com.unilever.julia.data.model.Conversations
import com.unilever.julia.data.model.InovacaoProjetoModel
import com.unilever.julia.ui.base.BaseInteractorImpl
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.functions.Function
import org.apache.commons.lang3.StringUtils
import java.util.concurrent.TimeUnit

class InovacaoProjetosInteractorImpl
@Inject constructor(mDataManager: DataManager) : BaseInteractorImpl(mDataManager), InovacaoProjetosInteractor {

    var mGson: Gson = Gson()

    override fun getListaInovacoes(sessionCode: String, code: String, marcaId: String, categoriaId: String, commodityId: String): Observable<MutableList<InovacaoProjetoModel>> {
        return juliaUnileverApi().getProjectsInnovations(sessionCode, code, marcaId, categoriaId, commodityId)
            .delay(1, TimeUnit.SECONDS)
            .flatMap(Function<Conversations, Observable<MutableList<InovacaoProjetoModel>>> { conversations ->

                if (conversations.answers.isNullOrEmpty()) {
                    return@Function Observable.just(arrayListOf())
                }

                val answer : Answer = conversations.answers?.get(0) ?: Answer()

                val itemsArray = mGson.fromJson(StringUtils.trimToEmpty(answer.technicalText), Array<InovacaoProjetoModel>::class.java)

                return@Function Observable.just(itemsArray.toMutableList())
            })
    }
}