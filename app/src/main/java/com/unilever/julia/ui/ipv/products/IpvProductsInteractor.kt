package com.unilever.julia.ui.ipv.products

import com.unilever.julia.data.model.ipv.IpvContext
import com.unilever.julia.data.model.ipv.IpvProduct
import com.unilever.julia.ui.base.*
import io.reactivex.Observable

interface IpvProductsInteractor : BaseInteractor {

    fun getIpvProducts(sessionCode: String, ipvContext: IpvContext): Observable<List<IpvProduct>>
}