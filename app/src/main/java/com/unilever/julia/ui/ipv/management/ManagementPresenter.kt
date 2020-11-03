package com.unilever.julia.ui.ipv.management

import com.unilever.julia.components.model.IpvManagementCard
import com.unilever.julia.data.model.ipv.IpvContext
import com.unilever.julia.ui.base.*

interface ManagementPresenter<V : ManagementView, I : ManagementInteractor> : BasePresenter<V, I> {

    fun getIpvManagement(sessionCode: String, ipvContext: IpvContext)
    fun onClickNextContext(management: IpvManagementCard)
}