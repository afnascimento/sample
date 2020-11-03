package com.unilever.julia.ui.inovacao.projectsV2

import com.unilever.julia.dagger.*
import dagger.Binds
import dagger.Module

@Module
abstract class ProjectsModule {

    @ActivityScoped
    @Binds
    internal abstract fun getView(activity: ProjectsActivity): ProjectsView

    @ActivityScoped
    @Binds
    internal abstract fun getPresenter(presenter: ProjectsPresenterImpl<ProjectsView, ProjectsInteractor>): ProjectsPresenter<ProjectsView, ProjectsInteractor>

    @ActivityScoped
    @Binds
    //internal abstract fun getInteractor(interactor: ProjectsInteractorFirebase): ProjectsInteractor
    internal abstract fun getInteractor(interactor: ProjectsInteractorImpl): ProjectsInteractor
}