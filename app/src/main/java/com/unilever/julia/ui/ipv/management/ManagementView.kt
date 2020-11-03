package com.unilever.julia.ui.ipv.management

import com.unilever.julia.components.model.IpvManagementCard
import com.unilever.julia.data.model.ipv.IpvContext
import com.unilever.julia.data.model.ipv.IpvItem
import com.unilever.julia.ui.base.*

interface ManagementView : BaseView {

    fun setTitle(title: String)
    fun setHeader(card: IpvManagementCard)
    fun addItems(items: List<IpvManagementCard>)
    fun redirectIpvActivity(ipvContext: IpvContext, items: List<IpvItem>)
    fun redirectManagementActivity(ipvContext: IpvContext)
    fun setHintSearch(text: String)
}