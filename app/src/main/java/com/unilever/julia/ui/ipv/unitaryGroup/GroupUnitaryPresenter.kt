package com.unilever.julia.ui.ipv.unitaryGroup

import com.unilever.julia.data.model.goods.IpvGoodsGroup
import com.unilever.julia.data.model.ipv.IpvClient
import com.unilever.julia.data.model.ipv.IpvContext
import com.unilever.julia.ui.base.*

interface GroupUnitaryPresenter<V : GroupUnitaryView, I : GroupUnitaryInteractor> :
    BasePresenter<V, I> {

    fun init(sessionCode: String, ipvClient: IpvClient, ipvContext: IpvContext, description: String)

    fun onClick(ipvClient: IpvClient, ipvGoodsGroup: IpvGoodsGroup, description: String)
}