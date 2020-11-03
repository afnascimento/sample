package com.unilever.julia.ui.ipv.categoriesPriorities

import com.google.gson.Gson
import com.unilever.julia.ui.base.*

import com.unilever.julia.data.*
import com.unilever.julia.data.model.ipv.IpvCategories
import com.unilever.julia.data.model.ipv.IpvCategory
import com.unilever.julia.data.model.ipv.IpvContext
import com.unilever.julia.exception.EmptyDataException
import io.reactivex.Observable

import javax.inject.Inject

class CategoriesPrioritiesInteractorImpl
@Inject constructor(mDataManager: DataManager) : BaseInteractorImpl(mDataManager), CategoriesPrioritiesInteractor {

    private val mGson = Gson()

    override fun getIpvCategoriesPriorities(sessionCode: String, ipvContext: IpvContext): Observable<List<IpvCategory>> {
        return dataManager().juliaApi().getIpvConversations(
            sessionCode, ipvContext.code?: "", ipvContext.value ?: ""
        ).flatMap { conversations ->
            val model = mGson.fromJson<IpvCategories>(
                conversations.getAnswer().technicalText,
                IpvCategories::class.java
            )
            if (model.categories.isNullOrEmpty()) {
                throw EmptyDataException()
            }
            return@flatMap Observable.just(model.categories)
        }
    }
}