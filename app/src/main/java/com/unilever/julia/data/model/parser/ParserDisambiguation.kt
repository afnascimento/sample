package com.unilever.julia.data.model.parser

import com.unilever.julia.components.model.ButtonClickListenerModel
import com.unilever.julia.components.model.IComponentsModel
import com.unilever.julia.data.model.*
import com.unilever.julia.utils.Utils
import org.apache.commons.lang3.StringUtils

class ParserDisambiguation(conversations: Conversations) : IParserModel {

    private var model : ButtonDisambiguationModel

    init {
        val answer = conversations.getAnswer()

        model = ButtonDisambiguationModel()
        model.text = StringUtils.trimToEmpty(answer.text)

        for (opt in answer.options ?: arrayListOf()) {
            val option = ButtonDisambiguationModel.Option()
            val map = Utils.parseJsonToMap(StringUtils.trimToEmpty(opt.text))
            option.icon = map["font"] ?: ""
            option.color = map["color"] ?: ""
            option.title = opt.title ?: ""
            option.intent = map["intencao"] ?: ""
            option.event = map["event"] ?: ""
            option.param = map["param"] ?: ""
            option.text = map["text"] ?: ""
            model.options.add(option)
        }

        if (model.options.isNotEmpty()) {
            model.closeButton = model.getCloseIntent()

            for (it in model.getOptionsWithoutCloseIntent()) {
                val clickButton = ButtonClickListenerModel(
                    it.title,
                    it.intent,
                    it.event,
                    it.param
                )
                model.items.add(ButtonDisambiguationModel.Item(it.icon, it.color, clickButton, model.closeButton))
            }
        }
    }

    override fun getModel(): IComponentsModel {
        return model
    }

    override fun getText(): String {
        return model.text
    }
}