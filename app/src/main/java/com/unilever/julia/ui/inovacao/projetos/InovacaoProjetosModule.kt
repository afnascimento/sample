package com.unilever.julia.ui.inovacao.projetos

import com.unilever.julia.dagger.ActivityScoped
import dagger.Binds
import dagger.Module

@Module
abstract class InovacaoProjetosModule {

    @ActivityScoped
    @Binds
    internal abstract fun getView(activity: InovacaoProjetosActivity): InovacaoProjetosView

    @ActivityScoped
    @Binds
    internal abstract fun getPresenter(presenter: InovacaoProjetosPresenterImpl<InovacaoProjetosView, InovacaoProjetosInteractor>): InovacaoProjetosPresenter<InovacaoProjetosView, InovacaoProjetosInteractor>

    @ActivityScoped
    @Binds
    internal abstract fun getInteractor(interactor: InovacaoProjetosInteractorImpl): InovacaoProjetosInteractor
}