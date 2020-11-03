package com.unilever.julia.ui.tutorial

import com.unilever.julia.ui.base.BasePresenter

interface TutorialPresenter<V : TutorialView, I : TutorialInteractor> : BasePresenter<V, I> {
    fun saveTutorialFinish()
    fun getNews()
}