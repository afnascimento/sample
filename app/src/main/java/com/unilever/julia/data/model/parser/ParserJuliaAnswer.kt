package com.unilever.julia.data.model.parser

import com.unilever.julia.components.model.IComponentsModel
import com.unilever.julia.data.model.*
import org.apache.commons.lang3.StringUtils

class ParserJuliaAnswer(isCopyTextOnClick : Boolean, conversations: Conversations) : IParserModel {

    private var model : JuliaAnswerModel
    init {
        val answer = conversations.getAnswer()
        val text = StringUtils.trimToEmpty(answer.text)
        model = JuliaAnswerModel(text, isCopyTextOnClick)
    }

    override fun getModel(): IComponentsModel {
        return model
    }

    override fun getText(): String {
        return model.text
    }
}