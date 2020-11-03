package com.unilever.julia.ui.ipv.products

import com.unilever.julia.dagger.*
import dagger.Binds
import dagger.Module

@Module
abstract class IpvProductsModule {

    @ActivityScoped
    @Binds
    internal abstract fun getView(activity: IpvProductsActivity): IpvProductsView

    @ActivityScoped
    @Binds
    internal abstract fun getPresenter(presenter: IpvProductsPresenterImpl<IpvProductsView, IpvProductsInteractor>): IpvProductsPresenter<IpvProductsView, IpvProductsInteractor>

    @ActivityScoped
    @Binds
    internal abstract fun getInteractor(interactor: IpvProductsInteractorImpl): IpvProductsInteractor
}