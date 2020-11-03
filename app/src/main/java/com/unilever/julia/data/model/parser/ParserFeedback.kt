package com.unilever.julia.data.model.parser

import com.unilever.julia.components.model.IComponentsModel
import com.unilever.julia.data.model.*
import org.apache.commons.lang3.StringUtils

class ParserFeedback(conversations: Conversations) : IParserModel {

    private var model : FeedbackOptionsModel

    init {
        val answer = conversations.getAnswer()

        model = FeedbackOptionsModel()
        model.text = StringUtils.trimToEmpty(answer.text)

        for (opt in answer.options()) {
            val title = opt.title ?: ""
            val intencao = opt.text ?: ""
            model.items.add(FeedbackOptionsModel.Item(title, intencao))
        }
    }

    override fun getModel(): IComponentsModel {
        return model
    }

    override fun getText(): String {
        return model.text
    }
}