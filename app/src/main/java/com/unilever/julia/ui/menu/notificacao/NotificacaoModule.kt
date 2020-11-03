package com.unilever.julia.ui.menu.notificacao

import com.unilever.julia.dagger.ActivityScoped
import dagger.Binds
import dagger.Module

@Module
abstract class NotificacaoModule {

    @ActivityScoped
    @Binds
    internal abstract fun getView(activity: NotificacaoActivity): NotificacaoView

    @ActivityScoped
    @Binds
    internal abstract fun getPresenter(presenter: NotificacaoPresenterImpl<NotificacaoView, NotificacaoInteractor>): NotificacaoPresenter<NotificacaoView, NotificacaoInteractor>

    @ActivityScoped
    @Binds
    //internal abstract fun getInteractor(interactor: NotificacaoInteractorFirebase): NotificacaoInteractor
    internal abstract fun getInteractor(interactor: NotificacaoInteractorImpl): NotificacaoInteractor
}