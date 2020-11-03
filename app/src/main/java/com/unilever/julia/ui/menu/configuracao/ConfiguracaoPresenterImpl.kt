package com.unilever.julia.ui.menu.configuracao

import com.unilever.julia.data.model.InitDataConfiguracao
import com.unilever.julia.ui.base.BasePresenterImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ConfiguracaoPresenterImpl<V : ConfiguracaoView, I : ConfiguracaoInteractor>
@Inject constructor(mView: V, mInteractor: I) : BasePresenterImpl<V, I>(mView, mInteractor), ConfiguracaoPresenter<V, I> {

    override fun initConfiguration() {
        getView().addDisposable(
            getInteractor().getInitData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(object : DisposableObserver<InitDataConfiguracao>() {
                    override fun onNext(value: InitDataConfiguracao) {
                        getView().setConfigTextToSpeech(value.isTextToSpeech)
                        getView().setConfigOutlook(value.isOutlook)
                        getView().setConfigIPV(value.isIPV)
                    }

                    override fun onComplete() {
                    }

                    override fun onError(e: Throwable) {
                    }
                })
        )
    }

    override fun saveTextToSpeech(enabled: Boolean) {
        getView().addDisposable(
            getInteractor().saveTextToSpeech(enabled)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(object : DisposableObserver<Boolean>() {
                    override fun onNext(value: Boolean) {
                        getInteractor().firebaseAnalytics().addEventVoiceSettingEnabled(enabled)
                    }

                    override fun onComplete() {
                    }

                    override fun onError(e: Throwable) {
                    }
                })
        )
    }

    override fun saveOutlook(enabled: Boolean) {
        getView().addDisposable(
            getInteractor().saveOutlook(enabled)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(object : DisposableObserver<Boolean>() {
                    override fun onNext(value: Boolean) {
                        //getInteractor().firebaseAnalytics().addEventVoiceSettingEnabled(enabled)
                    }

                    override fun onComplete() {
                    }

                    override fun onError(e: Throwable) {
                    }
                })
        )
    }

    override fun saveIPV(enabled: Boolean) {
        getView().addDisposable(
            getInteractor().saveIPV(enabled)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(object : DisposableObserver<Boolean>() {
                    override fun onNext(value: Boolean) {
                        //getInteractor().firebaseAnalytics().addEventVoiceSettingEnabled(enabled)
                    }

                    override fun onComplete() {
                    }

                    override fun onError(e: Throwable) {
                    }
                })
        )
    }
}