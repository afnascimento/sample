package com.unilever.julia.ui.ipv.management

import com.unilever.julia.data.model.ipv.IpvContext
import com.unilever.julia.data.model.ipv.manager.IpvSummary
import com.unilever.julia.ui.base.*
import io.reactivex.Observable

interface ManagementInteractor : BaseInteractor {

    fun getIpvManagement(sessionCode: String, ipvContext: IpvContext): Observable<IpvSummary>
}