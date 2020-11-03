package com.unilever.julia.data.model.parser

import com.unilever.julia.components.model.IComponentsModel
import com.unilever.julia.data.model.*
import org.apache.commons.lang3.StringUtils

class ParserReuniaoOutlook(sessionCode : String, conversations: Conversations) : IParserModel {

    private var model : ReuniaoOutlookModel

    init {
        val answer = conversations.getAnswer()

        model = ReuniaoOutlookModel()
        model.text = StringUtils.trimToEmpty(answer.text)
        model.sessionCode = sessionCode
    }

    override fun getModel(): IComponentsModel {
        return model
    }

    override fun getText(): String {
        return model.text
    }
}