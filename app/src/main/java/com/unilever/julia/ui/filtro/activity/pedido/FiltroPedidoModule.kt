package com.unilever.julia.ui.filtro.activity.pedido

import com.unilever.julia.dagger.ActivityScoped
import dagger.Binds
import dagger.Module

@Module
abstract class FiltroPedidoModule {

    @ActivityScoped
    @Binds
    internal abstract fun getView(activity: FiltroPedidoActivity): FiltroPedidoView

    @ActivityScoped
    @Binds
    internal abstract fun getPresenter(presenter: FiltroPedidoPresenterImpl<FiltroPedidoView, FiltroPedidoInteractor>): FiltroPedidoPresenter<FiltroPedidoView, FiltroPedidoInteractor>

    @ActivityScoped
    @Binds
    internal abstract fun getInteractor(interactor: FiltroPedidoInteractorImpl): FiltroPedidoInteractor
}