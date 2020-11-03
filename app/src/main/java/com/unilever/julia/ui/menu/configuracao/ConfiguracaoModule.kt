package com.unilever.julia.ui.menu.configuracao

import com.unilever.julia.dagger.ActivityScoped
import dagger.Binds
import dagger.Module

@Module
abstract class ConfiguracaoModule {

    @ActivityScoped
    @Binds
    internal abstract fun getView(activity: ConfiguracaoActivity): ConfiguracaoView

    @ActivityScoped
    @Binds
    internal abstract fun getPresenter(presenter: ConfiguracaoPresenterImpl<ConfiguracaoView, ConfiguracaoInteractor>): ConfiguracaoPresenter<ConfiguracaoView, ConfiguracaoInteractor>

    @ActivityScoped
    @Binds
    internal abstract fun getInteractor(interactor: ConfiguracaoInteractorImpl): ConfiguracaoInteractor
}