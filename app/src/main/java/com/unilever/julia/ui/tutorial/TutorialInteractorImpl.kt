package com.unilever.julia.ui.tutorial

import com.google.gson.Gson
import com.unilever.julia.data.DataManager
import com.unilever.julia.data.api.JuliaIntent
import com.unilever.julia.data.model.News
import com.unilever.julia.ui.base.BaseInteractorImpl
import io.reactivex.Observable
import org.apache.commons.lang3.StringUtils
import javax.inject.Inject

class TutorialInteractorImpl
@Inject constructor(dataManager: DataManager) : BaseInteractorImpl(dataManager), TutorialInteractor {

    override fun saveTutorialFinish(): Observable<Boolean> {
        return Observable.fromCallable {
            dataManager().preferences().saveTutorialFinish()
            return@fromCallable true
        }
    }

    private val mGson = Gson()

    override fun getNews(): Observable<News> {
        return sendIntent("", JuliaIntent.Intent.NOVIDADES_00)
            .flatMap { conversations ->
                val json = StringUtils.trimToEmpty(conversations.getAnswer().technicalText)
                val news = mGson.fromJson(json, News::class.java)
                return@flatMap Observable.just(news)
            }
    }
}