package com.unilever.julia.ui.inovacao.detalhe

import com.unilever.julia.dagger.ActivityScoped
import dagger.Binds
import dagger.Module

@Module
abstract class InovacaoDetalheModule {

    @ActivityScoped
    @Binds
    internal abstract fun getView(activity: InovacaoDetalheActivity): InovacaoDetalheView

    @ActivityScoped
    @Binds
    internal abstract fun getPresenter(presenter: InovacaoDetalhePresenterImpl<InovacaoDetalheView, InovacaoDetalheInteractor>): InovacaoDetalhePresenter<InovacaoDetalheView, InovacaoDetalheInteractor>

    @ActivityScoped
    @Binds
    internal abstract fun getInteractor(interactor: InovacaoDetalheInteractorImpl): InovacaoDetalheInteractor
}