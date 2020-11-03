package com.unilever.julia.ui.boletim.multiple

import com.unilever.julia.dagger.*
import dagger.Binds
import dagger.Module

@Module
abstract class BoletimMultipleModule {

    @ActivityScoped
    @Binds
    internal abstract fun getView(activity: BoletimMultipleActivity): BoletimMultipleView

    @ActivityScoped
    @Binds
    internal abstract fun getPresenter(presenter: BoletimMultiplePresenterImpl<BoletimMultipleView, BoletimMultipleInteractor>): BoletimMultiplePresenter<BoletimMultipleView, BoletimMultipleInteractor>

    @ActivityScoped
    @Binds
    internal abstract fun getInteractor(interactor: BoletimMultipleInteractorImpl): BoletimMultipleInteractor
}