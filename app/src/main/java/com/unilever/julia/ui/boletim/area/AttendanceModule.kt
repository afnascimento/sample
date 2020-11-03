package com.unilever.julia.ui.boletim.area

import com.unilever.julia.dagger.*
import dagger.Binds
import dagger.Module

@Module
abstract class AttendanceModule {

    @ActivityScoped
    @Binds
    internal abstract fun getView(activity: AttendanceActivity): AttendanceView

    @ActivityScoped
    @Binds
    internal abstract fun getPresenter(presenter: AttendancePresenterImpl<AttendanceView, AttendanceInteractor>): AttendancePresenter<AttendanceView, AttendanceInteractor>

    @ActivityScoped
    @Binds
    internal abstract fun getInteractor(interactor: AttendanceInteractorImpl): AttendanceInteractor
}