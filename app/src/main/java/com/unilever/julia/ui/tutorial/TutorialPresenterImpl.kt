package com.unilever.julia.ui.tutorial

import com.unilever.julia.data.model.News
import com.unilever.julia.ui.base.BasePresenterImpl
import com.unilever.julia.components.LoadView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TutorialPresenterImpl<V : TutorialView, I : TutorialInteractor>
@Inject constructor(mView: V, mInteractor: I) : BasePresenterImpl<V, I>(mView, mInteractor), TutorialPresenter<V, I> {

    override fun getNews() {
        getView().addDisposable(
            getInteractor().getNews()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    getView().showLoadView()
                }
                .subscribeWith(object : DisposableObserver<News>() {
                    override fun onNext(value: News) {
                        getView().addItems(value.items)
                    }

                    override fun onComplete() {
                        getView().hideLoadView()
                    }

                    override fun onError(e: Throwable) {
                        getView().hideLoadView()
                        getView().onErrorHandler(e, LoadView.OnClickListener { getNews() })
                    }
                })
        )
    }

    override fun saveTutorialFinish() {
        getView().addDisposable(
            getInteractor().saveTutorialFinish()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    getView().showLoadView()
                }
                .subscribeWith(object : DisposableObserver<Boolean>() {
                    override fun onNext(value: Boolean) {
                    }

                    override fun onComplete() {
                        getView().redirectChat()
                    }

                    override fun onError(e: Throwable) {
                    }
                })
        )
    }
}