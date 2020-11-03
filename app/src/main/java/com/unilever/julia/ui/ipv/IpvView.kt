package com.unilever.julia.ui.ipv

import com.unilever.julia.components.IpvTabLayout
import com.unilever.julia.data.model.ipv.IpvType
import com.unilever.julia.data.model.ipv.IpvClient
import com.unilever.julia.data.model.ipv.IpvContext
import com.unilever.julia.data.model.ipv.IpvOffer
import com.unilever.julia.ui.base.*

interface IpvView : BaseView  {

    fun addTabs(tabs: List<IpvTabLayout.Item>)
    fun addPages(pages: List<IpvType>)
    fun setTitleToolbar(text: String)
    fun onClientFocusClick(ipvClient: IpvClient)
    fun onClientPrioritiesClick(ipvClient: IpvClient)
    fun redirectCategoriesPrioritiesActivity(ipvClient: IpvClient)
    fun redirectGroupUnitaryActivity(
        ipvClient: IpvClient,
        ipvContext: IpvContext,
        headerDescription: String
    )

    fun onIpvOfferClick(item: IpvOffer)
    fun onClientInnovationClick(ipvClient: IpvClient)
    fun redirectIpvProductsActivity(
        ipvClient: IpvClient,
        ipvContext: IpvContext,
        headerDescription: String
    )
}