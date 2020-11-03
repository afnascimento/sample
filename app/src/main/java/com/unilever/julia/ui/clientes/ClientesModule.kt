package com.unilever.julia.ui.clientes

import com.unilever.julia.dagger.*
import dagger.Binds
import dagger.Module

@Module
abstract class ClientesModule {

    @ActivityScoped
    @Binds
    internal abstract fun getView(activity: ClientesActivity): ClientesView

    @ActivityScoped
    @Binds
    internal abstract fun getPresenter(presenter: ClientesPresenterImpl<ClientesView, ClientesInteractor>): ClientesPresenter<ClientesView, ClientesInteractor>

    @ActivityScoped
    @Binds
    internal abstract fun getInteractor(interactor: ClientesInteractorImpl): ClientesInteractor
}