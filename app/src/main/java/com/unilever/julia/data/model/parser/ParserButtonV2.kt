package com.unilever.julia.data.model.parser

import com.unilever.julia.data.model.ButtonHorizontalModel
import com.unilever.julia.data.model.Conversations
import com.unilever.julia.components.model.IComponentsModel
import com.unilever.julia.components.ButtonHorizontal
import com.unilever.julia.utils.Utils
import org.apache.commons.lang3.StringUtils

class ParserButtonV2(conversations: Conversations) : IParserModel {

    private var model : ButtonHorizontalModel

    init {
        val answer = conversations.getAnswer()

        model = ButtonHorizontalModel()
        model.text = StringUtils.trimToEmpty(answer.text)

        for (opt in answer.options ?: arrayListOf()) {
            val map = Utils.parseJsonToMap(StringUtils.trimToEmpty(opt.text))
            val icon = map["font"] ?: ""
            val color = map["color"] ?: ""
            val title = opt.title ?: ""
            val intencao = map["intencao"] ?: ""
            val event = map["event"] ?: ""
            val param = map["param"] ?: ""
            val lancamento = Utils.parseStringToBoolean(map["lancamento"] ?: "false")
            model.items.add(ButtonHorizontal.Item(icon, color, title, intencao, event, param, lancamento))
        }
    }

    override fun getModel(): IComponentsModel {
        return model
    }

    override fun getText(): String {
        return model.text
    }
}