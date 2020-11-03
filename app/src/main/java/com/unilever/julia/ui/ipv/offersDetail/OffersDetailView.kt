package com.unilever.julia.ui.ipv.offersDetail

import com.unilever.julia.data.model.ipv.IpvOfferProduct
import com.unilever.julia.ui.base.*

interface OffersDetailView : BaseView {

    fun addItems(items: List<IpvOfferProduct>)
    fun setIconOffer(icon: String)
    fun setCommodity(commodity: String)
    fun clearEditText()
    fun goneLoadView()
    fun showContentViews()
    fun hideContentViews()
    fun showLoadViewEmpty()
    fun visibleLoadView()
}