package com.unilever.julia.ui.ipv.products

import com.google.gson.Gson
import com.unilever.julia.ui.base.*

import com.unilever.julia.data.*
import com.unilever.julia.data.model.ipv.IpvContext
import com.unilever.julia.data.model.ipv.IpvProduct
import com.unilever.julia.data.model.ipv.IpvProducts
import com.unilever.julia.exception.EmptyDataException
import io.reactivex.Observable

import javax.inject.Inject

class IpvProductsInteractorImpl
@Inject constructor(mDataManager: DataManager) : BaseInteractorImpl(mDataManager), IpvProductsInteractor {

    private val mGson = Gson()

    override fun getIpvProducts(sessionCode: String, ipvContext: IpvContext): Observable<List<IpvProduct>> {
        return dataManager().juliaApi().getIpvConversations(
            sessionCode, ipvContext.code?: "", ipvContext.value ?: ""
        ).flatMap { conversations ->
            val model = mGson.fromJson<IpvProducts>(
                conversations.getAnswer().technicalText,
                IpvProducts::class.java
            )
            if (model.products.isNullOrEmpty()) {
                throw EmptyDataException()
            }
            return@flatMap Observable.just(model.products)
        }
    }
}