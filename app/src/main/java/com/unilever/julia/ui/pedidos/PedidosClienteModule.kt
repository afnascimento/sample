package com.unilever.julia.ui.pedidos

import com.unilever.julia.dagger.ActivityScoped
import dagger.Binds
import dagger.Module

@Module
abstract class PedidosClienteModule {

    @ActivityScoped
    @Binds
    internal abstract fun getView(activity: PedidosClienteActivity): PedidosClienteView

    @ActivityScoped
    @Binds
    internal abstract fun getPresenter(presenter: PedidosClientePresenterImpl<PedidosClienteView, PedidosClienteInteractor>): PedidosClientePresenter<PedidosClienteView, PedidosClienteInteractor>

    @ActivityScoped
    @Binds
    internal abstract fun getInteractor(interactor: PedidosClienteInteractorImpl): PedidosClienteInteractor
}