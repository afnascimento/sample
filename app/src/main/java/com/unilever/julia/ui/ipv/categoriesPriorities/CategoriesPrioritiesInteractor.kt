package com.unilever.julia.ui.ipv.categoriesPriorities

import com.unilever.julia.data.model.ipv.IpvCategory
import com.unilever.julia.data.model.ipv.IpvContext
import com.unilever.julia.ui.base.*
import io.reactivex.Observable

interface CategoriesPrioritiesInteractor : BaseInteractor {

    fun getIpvCategoriesPriorities(
        sessionCode: String,
        ipvContext: IpvContext
    ): Observable<List<IpvCategory>>
}