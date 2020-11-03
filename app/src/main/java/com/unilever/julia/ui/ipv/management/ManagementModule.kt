package com.unilever.julia.ui.ipv.management

import com.unilever.julia.dagger.*
import dagger.Binds
import dagger.Module

@Module
abstract class ManagementModule {

    @ActivityScoped
    @Binds
    internal abstract fun getView(activity: ManagementActivity): ManagementView

    @ActivityScoped
    @Binds
    internal abstract fun getPresenter(presenter: ManagementPresenterImpl<ManagementView, ManagementInteractor>): ManagementPresenter<ManagementView, ManagementInteractor>

    @ActivityScoped
    @Binds
    internal abstract fun getInteractor(interactor: ManagementInteractorImpl): ManagementInteractor
}