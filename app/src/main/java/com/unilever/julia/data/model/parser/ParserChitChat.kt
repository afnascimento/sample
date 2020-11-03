package com.unilever.julia.data.model.parser

import com.unilever.julia.components.model.IComponentsModel
import com.unilever.julia.data.model.*
import org.apache.commons.lang3.StringUtils

class ParserChitChat(conversations: Conversations) : IParserModel {

    private var model : ChitChatModel

    init {
        val answer = conversations.getAnswer()

        model = ChitChatModel()
        model.text = StringUtils.trimToEmpty(answer.text)
        for (opt in answer.options ?: arrayListOf()) {
            model.items.add(ChitChatModel.Item(StringUtils.trimToEmpty(opt.title), StringUtils.trimToEmpty(opt.text)))
        }
    }

    override fun getModel(): IComponentsModel {
        return model
    }

    override fun getText(): String {
        return model.text
    }
}