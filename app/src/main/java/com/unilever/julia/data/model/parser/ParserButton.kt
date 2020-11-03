package com.unilever.julia.data.model.parser

import com.unilever.julia.data.model.ButtonModel
import com.unilever.julia.data.model.Conversations
import com.unilever.julia.components.model.IComponentsModel
import com.unilever.julia.utils.Utils
import org.apache.commons.lang3.StringUtils

class ParserButton(conversations: Conversations) : IParserModel {

    private var model : ButtonModel

    init {
        val answer = conversations.getAnswer()

        model = ButtonModel()
        model.text = StringUtils.trimToEmpty(answer.text)

        for (opt in answer.options()) {
            val map = Utils.parseJsonToMap(StringUtils.trimToEmpty(opt.text))
            val icon = map["font"] ?: ""
            val color = map["color"] ?: ""
            val title = opt.title ?: ""
            val intencao = map["intencao"] ?: ""
            model.items.add(ButtonModel.Item(icon, color, title, intencao))
        }
    }

    override fun getModel(): IComponentsModel {
        return model
    }

    override fun getText(): String {
        return model.text
    }
}