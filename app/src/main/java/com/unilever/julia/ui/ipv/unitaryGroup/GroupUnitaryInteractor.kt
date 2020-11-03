package com.unilever.julia.ui.ipv.unitaryGroup

import com.unilever.julia.data.model.goods.Good
import com.unilever.julia.data.model.ipv.IpvContext
import com.unilever.julia.ui.base.*
import io.reactivex.Observable

interface GroupUnitaryInteractor : BaseInteractor {

    fun getIpvGoods(sessionCode: String, ipvContext: IpvContext): Observable<Good>
}