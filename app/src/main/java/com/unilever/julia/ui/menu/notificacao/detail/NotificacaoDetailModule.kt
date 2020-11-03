package com.unilever.julia.ui.menu.notificacao.detail

import com.unilever.julia.dagger.ActivityScoped
import dagger.Binds
import dagger.Module

@Module
abstract class NotificacaoDetailModule {

    @ActivityScoped
    @Binds
    internal abstract fun getView(activity: NotificacaoDetailActivity): NotificacaoDetailView

    @ActivityScoped
    @Binds
    internal abstract fun getPresenter(presenter: NotificacaoDetailPresenterImpl<NotificacaoDetailView, NotificacaoDetailInteractor>): NotificacaoDetailPresenter<NotificacaoDetailView, NotificacaoDetailInteractor>

    @ActivityScoped
    @Binds
    internal abstract fun getInteractor(interactor: NotificacaoDetailInteractorImpl): NotificacaoDetailInteractor
}