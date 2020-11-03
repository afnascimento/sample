package com.unilever.julia.ui.boletim.multiple

import com.unilever.julia.components.LoadView
import com.unilever.julia.data.model.bulletin.BulletinsMultipleFiltersResponse
import com.unilever.julia.components.boletim.AttendanceFilter
import com.unilever.julia.ui.base.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject

class BoletimMultiplePresenterImpl<V : BoletimMultipleView, I : BoletimMultipleInteractor>
@Inject constructor(mView: V, mInteractor: I) : BasePresenterImpl<V, I>(mView, mInteractor), BoletimMultiplePresenter<V, I> {

    override fun init(type: BulletinsMultipleFiltersResponse.Type, attendanceFilter: AttendanceFilter) {
        getView().addDisposable(
            getInteractor().getBulletinsMultipleFilters(type, attendanceFilter)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    getView().showLoadView()
                }
                .subscribeWith(object : DisposableObserver<List<String>>() {
                    override fun onNext(value: List<String>) {
                        getView().addItems(value)
                    }

                    override fun onComplete() {
                        getView().hideLoadView()
                    }

                    override fun onError(e: Throwable) {
                        getView().onErrorHandler(e, LoadView.OnClickListener { init(type, attendanceFilter) })
                    }
                })
        )
    }
}