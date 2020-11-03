package com.unilever.julia.data.model.parser

import com.google.gson.Gson
import com.unilever.julia.components.model.IComponentsModel
import com.unilever.julia.data.model.*
import org.apache.commons.lang3.StringUtils

class ParserCardStatusPedido(gson : Gson, conversations: Conversations) : IParserModel {

    private var model : StatusPedidoModel

    init {
        val answer = conversations.getAnswer()

        model = StatusPedidoModel()
        model.text = StringUtils.trimToEmpty(answer.text)

        try {
            val arrayItems = gson.fromJson(
                StringUtils.trimToEmpty(answer.technicalText), Array<StatusPedidoModel.Item>::class.java
            )
            model.items = arrayItems.toMutableList()
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