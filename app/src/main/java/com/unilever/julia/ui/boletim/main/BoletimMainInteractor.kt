package com.unilever.julia.ui.boletim.main

import android.content.Context
import com.unilever.julia.components.TagsModel
import com.unilever.julia.components.model.BoletimChatModel
import com.unilever.julia.ui.base.*
import io.reactivex.Observable

interface BoletimMainInteractor : BaseInteractor {

    fun getBulletinsByFilter(context: Context,
                             territory: String,
                             attendance : List<TagsModel>,
                             brand : List<TagsModel>,
                             category : List<TagsModel>,
                             commodity : List<TagsModel>,
                             customer : List<TagsModel>,
                             channel : List<TagsModel>): Observable<BoletimChatModel>
}