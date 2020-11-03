package com.unilever.julia.data.model.parser

import com.unilever.julia.components.model.ButtonClickListenerModel
import com.unilever.julia.components.model.IComponentsModel
import com.unilever.julia.data.database.entity.Customer
import com.unilever.julia.data.model.*
import com.unilever.julia.utils.Utils
import org.apache.commons.lang3.StringUtils

class ParserDisambiguationPorCliente(conversations: Conversations,
                                     customers: MutableList<Customer>,
                                     closeButton : ButtonClickListenerModel
) : IParserModel {

    private var model : AutoCompleteCustomerModel

    init {
        val answer = conversations.getAnswer()

        model = AutoCompleteCustomerModel()
        model.text = StringUtils.trimToEmpty(answer.text)
        model.hint = "Digite o código/razão social"
        model.items = customers
        model.closeButton = closeButton
        model.events.textCloseButton = closeButton.text

        val map = Utils.parseJsonToMap(StringUtils.trimToEmpty(answer.technicalText))
        model.intent = map["code"] ?: ""
    }

    override fun getModel(): IComponentsModel {
        return model
    }

    override fun getText(): String {
        return model.text
    }
}