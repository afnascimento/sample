package com.unilever.julia.ui.tutorial

import com.unilever.julia.dagger.ActivityScoped
import dagger.Binds
import dagger.Module

@Module
abstract class TutorialModule {

    @ActivityScoped
    @Binds
    internal abstract fun getView(activity: TutorialActivity): TutorialView

    @ActivityScoped
    @Binds
    internal abstract fun getPresenter(presenter: TutorialPresenterImpl<TutorialView, TutorialInteractor>): TutorialPresenter<TutorialView, TutorialInteractor>

    @ActivityScoped
    @Binds
    internal abstract fun getInteractor(interactor: TutorialInteractorImpl): TutorialInteractor
}