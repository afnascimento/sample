package com.unilever.julia.ui.status.pendentes

import com.unilever.julia.dagger.ActivityScoped
import dagger.Binds
import dagger.Module

@Module
abstract class StatusPendentesModule {

    @ActivityScoped
    @Binds
    internal abstract fun getView(activity: StatusPendentesActivity): StatusPendentesView

    @ActivityScoped
    @Binds
    internal abstract fun getPresenter(presenter: StatusPendentesPresenterImpl<StatusPendentesView, StatusPendentesInteractor>): StatusPendentesPresenter<StatusPendentesView, StatusPendentesInteractor>

    @ActivityScoped
    @Binds
    internal abstract fun getInteractor(interactor: StatusPendentesInteractorImpl): StatusPendentesInteractor
}