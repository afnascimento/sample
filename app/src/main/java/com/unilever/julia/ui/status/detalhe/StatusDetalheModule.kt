package com.unilever.julia.ui.status.detalhe

import com.unilever.julia.dagger.ActivityScoped
import dagger.Binds
import dagger.Module

@Module
abstract class StatusDetalheModule {

    @ActivityScoped
    @Binds
    internal abstract fun getView(activity: StatusDetalheActivity): StatusDetalheView

    @ActivityScoped
    @Binds
    internal abstract fun getPresenter(presenter: StatusDetalhePresenterImpl<StatusDetalheView, StatusDetalheInteractor>): StatusDetalhePresenter<StatusDetalheView, StatusDetalheInteractor>

    @ActivityScoped
    @Binds
    internal abstract fun getInteractor(interactor: StatusDetalheInteractorImpl): StatusDetalheInteractor
}