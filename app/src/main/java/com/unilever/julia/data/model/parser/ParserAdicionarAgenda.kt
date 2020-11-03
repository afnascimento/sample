package com.unilever.julia.data.model.parser

import com.unilever.julia.components.model.IComponentsModel
import com.unilever.julia.data.model.*
import org.apache.commons.lang3.StringUtils

class ParserAdicionarAgenda(conversations: Conversations) : IParserModel {

    private var model : AdicionarAgendaModel

    init {
        val answer = conversations.getAnswer()

        val textJulia = StringUtils.trimToEmpty(answer.text)

        // ASSUNTO
        val lastIndexOf1 = StringUtils.lastIndexOf(StringUtils.upperCase(textJulia), "ASSUNTO")
        val substring = StringUtils.substring(textJulia, lastIndexOf1 + "ASSUNTO".length)

        val lastIndexOf2 = StringUtils.lastIndexOf(StringUtils.upperCase(substring), "DATA")
        var textAssunto = StringUtils.substring(substring, 0, lastIndexOf2)
        textAssunto = StringUtils.remove(textAssunto, "-")
        textAssunto = StringUtils.remove(textAssunto, "\n")
        textAssunto = StringUtils.trim(textAssunto)

        // DATA
        var textData = StringUtils.substringAfterLast(StringUtils.upperCase(textJulia), "DATA")
        textData = StringUtils.remove(textData, "-")
        textData = StringUtils.remove(textData, "\n")
        textData = StringUtils.trim(textData)

        val items = arrayListOf<AdicionarAgendaModel.Item>()

        for (opt in answer.options ?: arrayListOf()) {
            val model = AdicionarAgendaModel.Item()

            model.title = StringUtils.trimToEmpty(opt.title)
            model.textAction = StringUtils.trimToEmpty(opt.text)
            model.action = opt.action ?: false
            model.assunto = textAssunto
            model.data = textData

            items.add(model)
        }

        model = AdicionarAgendaModel()
        model.text = StringUtils.trimToEmpty(answer.text)
        model.items = items
    }

    override fun getModel(): IComponentsModel {
        return model
    }

    override fun getText(): String {
        return model.text
    }
}