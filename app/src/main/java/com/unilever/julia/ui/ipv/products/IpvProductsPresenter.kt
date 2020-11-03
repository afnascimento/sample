package com.unilever.julia.ui.ipv.products

import com.unilever.julia.data.model.ipv.IpvClient
import com.unilever.julia.data.model.ipv.IpvContext
import com.unilever.julia.ui.base.*

interface IpvProductsPresenter<V : IpvProductsView, I : IpvProductsInteractor> :
    BasePresenter<V, I> {

    fun init(sessionCode: String, client: IpvClient, context: IpvContext, headerDescription: String)
}