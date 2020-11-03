package com.unilever.julia.data.model.parser

import com.unilever.julia.components.model.IComponentsModel
import com.unilever.julia.data.model.*
import com.unilever.julia.utils.Utils
import org.apache.commons.lang3.StringUtils

class ParserSugestaoEnvio(conversations: Conversations) : IParserModel {

    private var model : SugestaoModel
    init {
        val answer = conversations.getAnswer()

        val map = Utils.parseJsonToMap(StringUtils.trimToEmpty(answer.technicalText))
        val code = map["code"] ?: ""
        val text = StringUtils.trimToEmpty(answer.text)

        model = SugestaoModel(code, text)
    }

    override fun getModel(): IComponentsModel {
        return model
    }

    override fun getText(): String {
        return model.text
    }
}