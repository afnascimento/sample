package com.unilever.julia.ui.agenda

import com.unilever.julia.dagger.ActivityScoped
import dagger.Binds
import dagger.Module

@Module
abstract class AgendaModule {

    @ActivityScoped
    @Binds
    internal abstract fun getView(activity: AgendaActivity): AgendaView

    @ActivityScoped
    @Binds
    internal abstract fun getPresenter(presenter: AgendaPresenterImpl<AgendaView, AgendaInteractor>): AgendaPresenter<AgendaView, AgendaInteractor>

    @ActivityScoped
    @Binds
    internal abstract fun getInteractor(interactor: AgendaInteractorImpl): AgendaInteractor
}