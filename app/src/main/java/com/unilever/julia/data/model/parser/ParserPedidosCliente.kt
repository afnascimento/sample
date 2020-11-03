package com.unilever.julia.data.model.parser

import com.google.gson.Gson
import com.unilever.julia.components.model.IComponentsModel
import com.unilever.julia.data.model.*
import org.apache.commons.lang3.StringUtils

class ParserPedidosCliente(gson : Gson,
                           sessionCode: String,
                           conversations: Conversations) : IParserModel {

    private var model : PedidosClienteModel

    init {
        val answer = conversations.getAnswer()

        model = PedidosClienteModel()
        model.sessionCode = sessionCode
        model.text = StringUtils.trimToEmpty(answer.text)

        val response = gson.fromJson(StringUtils.trimToEmpty(answer.technicalText), ResponseCode::class.java)
        model.code = response.code ?: ""
        model.customerId = response.customerId ?: ""
    }

    override fun getModel(): IComponentsModel {
        return model
    }

    override fun getText(): String {
        return model.text
    }
}