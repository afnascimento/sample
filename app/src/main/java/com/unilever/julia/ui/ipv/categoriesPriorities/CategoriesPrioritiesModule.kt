package com.unilever.julia.ui.ipv.categoriesPriorities

import com.unilever.julia.dagger.*
import dagger.Binds
import dagger.Module

@Module
abstract class CategoriesPrioritiesModule {

    @ActivityScoped
    @Binds
    internal abstract fun getView(activity: CategoriesPrioritiesActivity): CategoriesPrioritiesView

    @ActivityScoped
    @Binds
    internal abstract fun getPresenter(presenter: CategoriesPrioritiesPresenterImpl<CategoriesPrioritiesView, CategoriesPrioritiesInteractor>): CategoriesPrioritiesPresenter<CategoriesPrioritiesView, CategoriesPrioritiesInteractor>

    @ActivityScoped
    @Binds
    internal abstract fun getInteractor(interactor: CategoriesPrioritiesInteractorImpl): CategoriesPrioritiesInteractor
}