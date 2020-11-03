package com.unilever.julia.ui.boletim.main

import android.content.Context
import com.google.gson.Gson
import com.unilever.julia.components.TagsModel
import com.unilever.julia.components.model.BoletimChatModel
import com.unilever.julia.ui.base.*

import com.unilever.julia.data.*
import com.unilever.julia.data.model.Conversations
import com.unilever.julia.data.model.parser.ParserBoletimChat
import io.reactivex.Observable

import javax.inject.Inject

class BoletimMainInteractorImpl
@Inject constructor(mDataManager: DataManager) : BaseInteractorImpl(mDataManager), BoletimMainInteractor {

    private val mGson = Gson()

    override fun getBulletinsByFilter(
        context: Context,
        territory: String,
        attendance : List<TagsModel>,
        brand : List<TagsModel>,
        category : List<TagsModel>,
        commodity : List<TagsModel>,
        customer : List<TagsModel>,
        channel : List<TagsModel>
    ): Observable<BoletimChatModel> {
        return dataManager().juliaApi().getBulletinsByFilter("",
            territory,
            getItems(attendance),
            getItems(brand),
            getItems(category),
            getItems(commodity),
            getItems(customer),
            getItems(channel)
        ).flatMap { conversations: Conversations ->
            val parser = ParserBoletimChat(context, mGson, conversations)
            val model: BoletimChatModel = parser.getModel() as BoletimChatModel
            return@flatMap Observable.just(model)
        }
    }

    private fun getItems(tags : List<TagsModel>): List<String> {
        val list = arrayListOf<String>()
        for (it in tags) {
            list.add(it.code)
        }
        return list
    }
}