package com.unilever.julia.ui.inovacao.gallery

import com.unilever.julia.dagger.*
import dagger.Binds
import dagger.Module

@Module
abstract class GalleryModule {

    @ActivityScoped
    @Binds
    internal abstract fun getView(activity: GalleryActivity): GalleryView

    @ActivityScoped
    @Binds
    internal abstract fun getPresenter(presenter: GalleryPresenterImpl<GalleryView, GalleryInteractor>): GalleryPresenter<GalleryView, GalleryInteractor>

    @ActivityScoped
    @Binds
    internal abstract fun getInteractor(interactor: GalleryInteractorImpl): GalleryInteractor
}