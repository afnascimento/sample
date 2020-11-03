package com.unilever.julia.ui.login

import com.unilever.julia.dagger.ActivityScoped
import dagger.Binds
import dagger.Module

@Module
abstract class LoginModule {

    @ActivityScoped
    @Binds
    internal abstract fun getView(activity: LoginActivity): LoginView

    @ActivityScoped
    @Binds
    internal abstract fun getPresenter(presenter: LoginPresenterImpl<LoginView, LoginInteractor>): LoginPresenter<LoginView, LoginInteractor>

    @ActivityScoped
    @Binds
    internal abstract fun getInteractor(interactor: LoginInteractorImpl): LoginInteractor
}