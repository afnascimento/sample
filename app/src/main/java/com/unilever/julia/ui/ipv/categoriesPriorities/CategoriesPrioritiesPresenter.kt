package com.unilever.julia.ui.ipv.categoriesPriorities

import com.unilever.julia.data.model.ipv.IpvCategory
import com.unilever.julia.data.model.ipv.IpvClient
import com.unilever.julia.ui.base.*

interface CategoriesPrioritiesPresenter<V : CategoriesPrioritiesView, I : CategoriesPrioritiesInteractor> : BasePresenter<V, I> {

    fun init(sessionCode: String, client: IpvClient)
    fun onClick(ipvClient: IpvClient, ipvCategory: IpvCategory)
}