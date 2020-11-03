package com.unilever.julia.ui.tutorial

import com.unilever.julia.data.model.News
import com.unilever.julia.ui.base.BaseInteractor
import io.reactivex.Observable

interface TutorialInteractor : BaseInteractor {
    fun saveTutorialFinish(): Observable<Boolean>
    fun getNews(): Observable<News>
}