package com.unilever.julia.ui.splash

import com.unilever.julia.dagger.ActivityScoped
import dagger.Binds
import dagger.Module

@Module
abstract class SplashModule {

    @ActivityScoped
    @Binds
    internal abstract fun getView(activity: SplashActivity): SplashView

    @ActivityScoped
    @Binds
    internal abstract fun getPresenter(presenter: SplashPresenterImpl<SplashView, SplashInteractor>): SplashPresenter<SplashView, SplashInteractor>

    @ActivityScoped
    @Binds
    internal abstract fun getInteractor(interactor: SplashInteractorImpl): SplashInteractor
}