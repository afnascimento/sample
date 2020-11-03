package com.unilever.julia.ui.inovacao.tradestoryEvaluation

import com.unilever.julia.dagger.*
import dagger.Binds
import dagger.Module

@Module
abstract class TradeStoryEvaluationModule {

    @ActivityScoped
    @Binds
    internal abstract fun getView(activity: TradeStoryEvaluationActivity): TradeStoryEvaluationView

    @ActivityScoped
    @Binds
    internal abstract fun getPresenter(presenter: TradeStoryEvaluationPresenterImpl<TradeStoryEvaluationView, TradeStoryEvaluationInteractor>): TradeStoryEvaluationPresenter<TradeStoryEvaluationView, TradeStoryEvaluationInteractor>

    @ActivityScoped
    @Binds
    internal abstract fun getInteractor(interactor: TradeStoryEvaluationInteractorImpl): TradeStoryEvaluationInteractor
}