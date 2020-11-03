package com.unilever.julia.ui.boletim.area

import com.unilever.julia.components.LoadView
import com.unilever.julia.components.boletim.AttendanceModel
import com.unilever.julia.components.boletim.AttendancesModel
import com.unilever.julia.ui.base.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject

class AttendancePresenterImpl<V : AttendanceView, I : AttendanceInteractor>
@Inject constructor(mView: V, mInteractor: I) : BasePresenterImpl<V, I>(mView, mInteractor), AttendancePresenter<V, I> {

    private var mData : AttendancesModel? = null

    override fun init(myTerritory : String) {
        getView().addDisposable(
            getInteractor().getAttendance(myTerritory)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    getView().showLoadView()
                }
                .subscribeWith(object : DisposableObserver<AttendancesModel>() {
                    override fun onNext(value: AttendancesModel) {
                        mData = value
                        setSelected(value.getSelected())
                        getView().initTabs()
                    }

                    override fun onComplete() {
                        getView().hideLoadView()
                    }

                    override fun onError(e: Throwable) {
                        getView().onErrorHandler(e, LoadView.OnClickListener { init(myTerritory) })
                    }
                })
        )
    }

    override fun getData(): AttendancesModel? {
        return mData
    }

    private var mSelected : AttendanceModel = AttendanceModel()

    private fun setSelected(selected : AttendanceModel) {
        mSelected = selected
    }

    override fun getSelected(): AttendanceModel {
        return mSelected
    }

    override fun onChangeAttendance(attendance: AttendanceModel) {
        setSelected(attendance)
        getView().updateSelectedAllFragments(attendance)
    }

    override fun applyAttendance() {
        getView().onReturnWithResult(mSelected)
    }
}