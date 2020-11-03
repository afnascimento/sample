package com.unilever.julia.data.model.parser

import com.google.gson.Gson
import com.unilever.julia.components.model.IComponentsModel
import com.unilever.julia.components.model.InnovationCardHorizontalModel
import com.unilever.julia.components.model.InnovationCardModel
import com.unilever.julia.components.model.InnovationCardVerticalModel
import com.unilever.julia.data.model.*
import org.apache.commons.lang3.StringUtils

class ParserCardInovacao(gson : Gson,
                         sessionCode: String,
                         conversations: Conversations) : IParserModel {

    private var model : IComponentsModel

    private var text : String

    init {
        val nextNode : String = conversations.getNextNode()

        model = if (nextNode.isNotEmpty()) {
            getModelHorizontal(gson, sessionCode, conversations, nextNode)
        } else {
            getModelVertical(gson, sessionCode, conversations)
        }

        text = StringUtils.trimToEmpty(conversations.getAnswer().text)
    }

    private fun getCards(gson : Gson, technicalText : String): MutableList<InnovationCardModel.Card> {
        return gson.fromJson(technicalText, Array<InnovationCardModel.Card>::class.java).toMutableList()
    }

    private fun getModelVertical(gson : Gson,
                                 sessionCode: String,
                                 conversations: Conversations) : InnovationCardVerticalModel {
        val answer = conversations.getAnswer()

        val model = InnovationCardVerticalModel()
        model.text = StringUtils.trimToEmpty(answer.text)

        val entity = conversations.getEntity()

        val cards = getCards(gson, StringUtils.trimToEmpty(answer.technicalText))

        for (card in cards) {

            val reference = getReference(card.entities, card.text, sessionCode, entity)

            model.items.add(InnovationCardVerticalModel.Item(card, reference))
        }

        return model
    }

    private fun getModelHorizontal(gson : Gson,
                                   sessionCode: String,
                                   conversations: Conversations,
                                   nextIntent : String) : InnovationCardHorizontalModel {
        val answer = conversations.getAnswer()

        val model = InnovationCardHorizontalModel()
        model.text = StringUtils.trimToEmpty(answer.text)

        val entity = conversations.getEntity()

        val cards = getCards(gson, StringUtils.trimToEmpty(answer.technicalText))

        for (card in cards) {

            val reference = getReference(nextIntent, card.text, sessionCode, entity)

            model.items.add(InnovationCardHorizontalModel.Item(card, reference))
        }

        return model
    }

    private fun getReference(nextIntent : String,
                             commodityId : String,
                             sessionCode : String,
                             entity : Entity?): InnovationCardModel.ContentReference {
        val reference = InnovationCardModel.ContentReference()

        reference.code = nextIntent

        if (entity != null) {
            reference.type = StringUtils.trimToEmpty(entity.type)

            if (StringUtils.containsIgnoreCase(reference.type, "marca")) {
                reference.marcaId = StringUtils.trimToEmpty(entity.name)
            } else if (StringUtils.containsIgnoreCase(reference.type, "categoria")) {
                reference.categoriaId = StringUtils.trimToEmpty(entity.name)
            }
        }

        reference.commodityId = commodityId
        reference.sessionCode = sessionCode

        return reference
    }

    override fun getModel(): IComponentsModel {
        return model
    }

    override fun getText(): String {
        return text
    }
}