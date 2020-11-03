package com.unilever.julia.ui.ipv.offersDetail

import com.unilever.julia.dagger.*
import dagger.Binds
import dagger.Module

@Module
abstract class OffersDetailModule {

    @ActivityScoped
    @Binds
    internal abstract fun getView(activity: OffersDetailActivity): OffersDetailView

    @ActivityScoped
    @Binds
    internal abstract fun getPresenter(presenter: OffersDetailPresenterImpl<OffersDetailView, OffersDetailInteractor>): OffersDetailPresenter<OffersDetailView, OffersDetailInteractor>

    @ActivityScoped
    @Binds
    internal abstract fun getInteractor(interactor: OffersDetailInteractorImpl): OffersDetailInteractor
}