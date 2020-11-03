package com.unilever.julia.data.model.parser

import com.unilever.julia.components.model.IComponentsModel
import com.unilever.julia.data.model.*

class ParserDisambiguationCodPedido(conversations: Conversations) : IParserModel {

    private var model : DisambiguationCodPedidoModel

    init {
        val answer = conversations.getAnswer()
        model = DisambiguationCodPedidoModel()
        model.text = answer.text ?: ""
    }

    override fun getModel(): IComponentsModel {
        return model
    }

    override fun getText(): String {
        return model.text
    }
}