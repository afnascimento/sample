package com.unilever.julia.ui.ipv.unitaryGroup

import com.unilever.julia.data.model.goods.Good
import com.unilever.julia.data.model.ipv.IpvClient
import com.unilever.julia.data.model.ipv.IpvContext
import com.unilever.julia.ui.base.*

interface GroupUnitaryView : BaseView {

    fun addGoods(good: Good)
    fun setHeaderCommodity(commodity: String)
    fun setHeaderCode(code: String)
    fun setHeaderName(name: String)
    fun setHeaderDescription(description: String)
    fun setHeaderButtonColor(color: String)
    fun redirectIpvProductsActivity(
        ipvClient: IpvClient,
        ipvContext: IpvContext,
        headerDescription: String
    )
}