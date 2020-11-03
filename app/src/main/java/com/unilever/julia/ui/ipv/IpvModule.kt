package com.unilever.julia.ui.ipv

import com.unilever.julia.dagger.*
import dagger.Binds
import dagger.Module

@Module
abstract class IpvModule {

    @ActivityScoped
    @Binds
    internal abstract fun getView(activity: IpvActivity): IpvView

    @ActivityScoped
    @Binds
    internal abstract fun getPresenter(presenter: IpvPresenterImpl<IpvView, IpvInteractor>): IpvPresenter<IpvView, IpvInteractor>

    @ActivityScoped
    @Binds
    //internal abstract fun getInteractor(interactor: IpvInteractorFirebase): IpvInteractor
    internal abstract fun getInteractor(interactor: IpvInteractorImpl): IpvInteractor
}