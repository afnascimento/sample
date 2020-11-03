package com.unilever.julia.ui.inovacao.projetos

import com.unilever.julia.data.model.InovacaoProjetoModel
import com.unilever.julia.ui.base.BaseView

interface InovacaoProjetosView : BaseView {
    fun addInovacoes(inovacoes: MutableList<InovacaoProjetoModel>)
}