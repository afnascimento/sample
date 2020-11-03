package com.unilever.julia.ui.ipv.products

import com.unilever.julia.data.model.ipv.IpvProduct
import com.unilever.julia.ui.base.*

interface IpvProductsView : BaseView {
    fun setHeaderCommodity(commodity: String)
    fun setHeaderCode(code: String)
    fun setHeaderName(name: String)
    fun setHeaderDescription(description: String)
    fun setHeaderButtonColor(color: String)
    fun addItems(items: List<IpvProduct>)
}