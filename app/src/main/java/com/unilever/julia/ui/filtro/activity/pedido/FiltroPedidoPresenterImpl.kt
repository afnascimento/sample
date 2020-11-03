package com.unilever.julia.ui.filtro.activity.pedido

import com.unilever.julia.ui.base.BasePresenterImpl
import javax.inject.Inject

class FiltroPedidoPresenterImpl<V : FiltroPedidoView, I : FiltroPedidoInteractor>
@Inject
constructor(mView: V, mInteractor: I) : BasePresenterImpl<V, I>(mView, mInteractor),
    FiltroPedidoPresenter<V, I>