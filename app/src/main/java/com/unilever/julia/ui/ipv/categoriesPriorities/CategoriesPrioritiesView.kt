package com.unilever.julia.ui.ipv.categoriesPriorities

import com.unilever.julia.data.model.ipv.IpvCategory
import com.unilever.julia.data.model.ipv.IpvClient
import com.unilever.julia.data.model.ipv.IpvContext
import com.unilever.julia.ui.base.*

interface CategoriesPrioritiesView : BaseView {

    fun addItems(items: List<IpvCategory>)
    fun setHeaderCommodity(commodity: String)
    fun setHeaderCode(code: String)
    fun setHeaderName(name: String)
    fun setHeaderDescription(description: String)
    fun setHeaderButtonColor(color: String)
    fun redirectGroupUnitaryActivity(
        ipvClient: IpvClient,
        ipvContext: IpvContext,
        headerDescription: String
    )
}