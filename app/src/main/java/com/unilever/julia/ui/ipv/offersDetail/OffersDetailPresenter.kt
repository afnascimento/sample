package com.unilever.julia.ui.ipv.offersDetail

import com.unilever.julia.data.model.ipv.IpvOffer
import com.unilever.julia.ui.base.*

interface OffersDetailPresenter<V : OffersDetailView, I : OffersDetailInteractor> : BasePresenter<V, I> {

    fun getIpvOffersProducts(sessionCode: String, ipvOffer : IpvOffer)
    fun filterItems(text: String)
}