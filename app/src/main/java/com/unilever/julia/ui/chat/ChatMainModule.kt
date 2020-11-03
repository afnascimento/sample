package com.unilever.julia.ui.chat

import com.unilever.julia.dagger.ActivityScoped
import dagger.Binds
import dagger.Module

@Module
abstract class ChatMainModule {

    @ActivityScoped
    @Binds
    internal abstract fun getView(activity: ChatMainActivity): ChatMainView

    @ActivityScoped
    @Binds
    internal abstract fun getPresenter(presenter: ChatMainPresenterImpl<ChatMainView, ChatMainInteractor>): ChatMainPresenter<ChatMainView, ChatMainInteractor>

    @ActivityScoped
    @Binds
    internal abstract fun getInteractor(interactor: ChatMainInteractorImpl): ChatMainInteractor
}