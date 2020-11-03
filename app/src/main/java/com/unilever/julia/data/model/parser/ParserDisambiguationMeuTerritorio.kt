package com.unilever.julia.data.model.parser

import com.google.gson.Gson
import com.unilever.julia.components.model.IComponentsModel
import com.unilever.julia.data.model.*
import com.unilever.julia.utils.Utils
import org.apache.commons.lang3.StringUtils

class ParserDisambiguationMeuTerritorio(gson: Gson, conversations: Conversations) : IParserModel {

    private var model : StatusPedidoCarteiraModel

    init {
        val answer = conversations.getAnswer()

        model = StatusPedidoCarteiraModel()
        model.text = StringUtils.trimToEmpty(answer.text)

        try {
            model.context = gson.fromJson(Utils.removeCharactersInStringJson(answer.technicalText ?: ""), StatusPedidoCarteiraModel.Context::class.java)
        } catch (e : Throwable) {
            e.printStackTrace()
        }
    }

    override fun getModel(): IComponentsModel {
        return model
    }

    override fun getText(): String {
        return model.text
    }
}