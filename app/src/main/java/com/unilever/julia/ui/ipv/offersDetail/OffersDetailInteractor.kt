package com.unilever.julia.ui.ipv.offersDetail

import com.unilever.julia.data.model.ipv.IpvOfferProduct
import com.unilever.julia.ui.base.*
import io.reactivex.Observable

interface OffersDetailInteractor : BaseInteractor {

    fun getIpvOffersProducts(
        sessionCode: String,
        intentCode: String,
        route: String
    ): Observable<List<IpvOfferProduct>>
}