package com.unilever.julia.ui.boletim.main

import com.unilever.julia.dagger.*
import dagger.Binds
import dagger.Module

@Module
abstract class BoletimMainModule {

    @ActivityScoped
    @Binds
    internal abstract fun getView(activity: BoletimMainActivity): BoletimMainView

    @ActivityScoped
    @Binds
    internal abstract fun getPresenter(presenter: BoletimMainPresenterImpl<BoletimMainView, BoletimMainInteractor>): BoletimMainPresenter<BoletimMainView, BoletimMainInteractor>

    @ActivityScoped
    @Binds
    internal abstract fun getInteractor(interactor: BoletimMainInteractorImpl): BoletimMainInteractor
}