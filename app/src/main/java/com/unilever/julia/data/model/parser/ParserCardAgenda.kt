package com.unilever.julia.data.model.parser

import com.google.gson.Gson
import com.unilever.julia.components.model.IComponentsModel
import com.unilever.julia.data.model.*
import org.apache.commons.lang3.StringUtils

class ParserCardAgenda(gson : Gson, conversations: Conversations) : IParserModel {

    private var model : AgendaModel

    init {
        val answer = conversations.getAnswer()

        var arrayItems : Array<AgendaModel.Item> = emptyArray()

        try {
            arrayItems = gson.fromJson(StringUtils.trimToEmpty(answer.technicalText), Array<AgendaModel.Item>::class.java)
        } catch (e : Exception) {
            e.printStackTrace()
        }

        val arrayItemsSorted : MutableList<AgendaModel.Item> = arrayListOf()
        if (arrayItems.isNotEmpty()) {

            val groupByArray = arrayItems.groupBy{ it.getData()}

            for (map in groupByArray) {
                var count = 0
                for (it in map.value) {
                    if (count == 0) {
                        it.dataVisible = true
                    }
                    it.dataDate = map.key
                    arrayItemsSorted.add(it)
                    count++
                }
            }
        }

        model = AgendaModel()
        model.text = StringUtils.trimToEmpty(answer.text)
        model.items = arrayItemsSorted.toMutableList()
    }

    override fun getModel(): IComponentsModel {
        return model
    }

    override fun getText(): String {
        return model.text
    }
}