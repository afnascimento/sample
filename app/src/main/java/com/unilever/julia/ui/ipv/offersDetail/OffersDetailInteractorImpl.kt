package com.unilever.julia.ui.ipv.offersDetail

import com.google.gson.Gson
import com.unilever.julia.ui.base.*

import com.unilever.julia.data.*
import com.unilever.julia.data.model.ipv.IpvOfferProduct
import com.unilever.julia.data.model.ipv.IpvOffersProducts
import io.reactivex.Observable

import javax.inject.Inject

class OffersDetailInteractorImpl
@Inject constructor(mDataManager: DataManager) : BaseInteractorImpl(mDataManager), OffersDetailInteractor {

    private val mGson = Gson()

    override fun getIpvOffersProducts(sessionCode: String,
                                      intentCode: String,
                                      route: String): Observable<List<IpvOfferProduct>> {
        return dataManager().juliaApi().getIpvConversations(sessionCode, intentCode, route)
            .flatMap { conversations ->
                val model = mGson.fromJson<IpvOffersProducts>(
                    conversations.getAnswer().technicalText,
                    IpvOffersProducts::class.java
                )
                val list : List<IpvOfferProduct> = model.products ?: emptyList()
                return@flatMap Observable.just(list)
            }
    }
}