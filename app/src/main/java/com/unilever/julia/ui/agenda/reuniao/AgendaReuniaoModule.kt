package com.unilever.julia.ui.agenda.reuniao

import com.unilever.julia.dagger.ActivityScoped
import dagger.Binds
import dagger.Module

@Module
abstract class AgendaReuniaoModule {

    @ActivityScoped
    @Binds
    internal abstract fun getView(activity: AgendaReuniaoActivity): AgendaReuniaoView

    @ActivityScoped
    @Binds
    internal abstract fun getPresenter(presenter: AgendaReuniaoPresenterImpl<AgendaReuniaoView, AgendaReuniaoInteractor>): AgendaReuniaoPresenter<AgendaReuniaoView, AgendaReuniaoInteractor>

    @ActivityScoped
    @Binds
    internal abstract fun getInteractor(interactor: AgendaReuniaoInteractorImpl): AgendaReuniaoInteractor
}