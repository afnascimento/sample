package com.unilever.julia.ui.filtro.activity.pedido

import com.unilever.julia.data.DataManager
import com.unilever.julia.ui.base.BaseInteractorImpl
import javax.inject.Inject

class FiltroPedidoInteractorImpl
@Inject
constructor(mDataManager: DataManager) : BaseInteractorImpl(mDataManager),
    FiltroPedidoInteractor {

}