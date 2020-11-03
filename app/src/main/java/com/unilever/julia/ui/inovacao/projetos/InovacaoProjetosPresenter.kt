package com.unilever.julia.ui.inovacao.projetos

import com.unilever.julia.ui.base.BasePresenter

interface InovacaoProjetosPresenter<V : InovacaoProjetosView, I : InovacaoProjetosInteractor> : BasePresenter<V, I> {
    fun getProjetosInovacoes(sessionCode: String, code: String, marcaId: String, categoriaId: String, commodityId: String)
}