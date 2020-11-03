package com.unilever.julia.data.model.parser

import com.unilever.julia.components.model.ButtonClickListenerModel
import com.unilever.julia.components.model.IComponentsModel
import com.unilever.julia.data.database.entity.Territory
import com.unilever.julia.data.model.*
import org.apache.commons.lang3.StringUtils

class ParserDisambiguationOutroTerritorio(conversations: Conversations,
                                          territories: MutableList<Territory>,
                                          closeButton : ButtonClickListenerModel
) : IParserModel {

    private var model : AutoCompleteTerritoryModel

    init {
        val answer = conversations.getAnswer()

        model = AutoCompleteTerritoryModel()
        model.intent = conversations.text ?: ""
        model.text = StringUtils.trimToEmpty(answer.text)
        model.hint = "Digite o código da região"
        model.items = territories
        model.closeButton = closeButton
        model.events.textCloseButton = closeButton.text
    }

    override fun getModel(): IComponentsModel {
        return model
    }

    override fun getText(): String {
        return model.text
    }
}