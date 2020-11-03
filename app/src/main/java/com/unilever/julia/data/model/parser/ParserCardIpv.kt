package com.unilever.julia.data.model.parser

import com.google.gson.Gson
import com.unilever.julia.components.model.IComponentsModel
import com.unilever.julia.data.model.*
import com.unilever.julia.data.model.ipv.IpvCardChat
import com.unilever.julia.utils.Utils
import org.apache.commons.lang3.StringUtils

class ParserCardIpv(gson : Gson, conversations: Conversations, user: User) : IParserModel {

    private var model : IpvCardChat

    init {
        val answer = conversations.getAnswer()

        model = IpvCardChat()
        try {
            val json = Utils.removeCharactersInStringJson(StringUtils.trimToEmpty(answer.technicalText))
            model = gson.fromJson(json, IpvCardChat::class.java)
        } catch (e : Exception) {
            model.visibleCard = false
        }
        model.nameIpv = user.getUsername()
        model.text = StringUtils.trimToEmpty(answer.text)
    }

    override fun getModel(): IComponentsModel {
        return model
    }

    override fun getText(): String {
        return model.text
    }
}