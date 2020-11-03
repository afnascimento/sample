package com.unilever.julia.ui.ipv.unitaryGroup

import com.unilever.julia.dagger.*
import dagger.Binds
import dagger.Module

@Module
abstract class GroupUnitaryModule {

    @ActivityScoped
    @Binds
    internal abstract fun getView(activity: GroupUnitaryActivity): GroupUnitaryView

    @ActivityScoped
    @Binds
    internal abstract fun getPresenter(presenter: GroupUnitaryPresenterImpl<GroupUnitaryView, GroupUnitaryInteractor>): GroupUnitaryPresenter<GroupUnitaryView, GroupUnitaryInteractor>

    @ActivityScoped
    @Binds
    internal abstract fun getInteractor(interactor: GroupUnitaryInteractorImpl): GroupUnitaryInteractor
}