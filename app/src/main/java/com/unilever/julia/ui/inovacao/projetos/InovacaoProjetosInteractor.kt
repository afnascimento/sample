package com.unilever.julia.ui.inovacao.projetos

import com.unilever.julia.data.model.InovacaoProjetoModel
import com.unilever.julia.ui.base.BaseInteractor
import io.reactivex.Observable

interface InovacaoProjetosInteractor : BaseInteractor {
    fun getListaInovacoes(sessionCode: String, code: String, marcaId: String, categoriaId: String, commodityId: String): Observable<MutableList<InovacaoProjetoModel>>
}