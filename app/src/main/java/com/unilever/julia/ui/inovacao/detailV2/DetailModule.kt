package com.unilever.julia.ui.inovacao.detailV2

import com.unilever.julia.dagger.*
import dagger.Binds
import dagger.Module

@Module
abstract class DetailModule {

    @ActivityScoped
    @Binds
    internal abstract fun getView(activity: DetailActivity): DetailView

    @ActivityScoped
    @Binds
    internal abstract fun getPresenter(presenter: DetailPresenterImpl<DetailView, DetailInteractor>): DetailPresenter<DetailView, DetailInteractor>

    @ActivityScoped
    @Binds
    //internal abstract fun getInteractor(interactor: DetailInteractorFirebase): DetailInteractor
    internal abstract fun getInteractor(interactor: DetailInteractorImpl): DetailInteractor
}