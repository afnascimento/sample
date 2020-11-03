package com.unilever.julia.ui.boletim.main

import com.unilever.julia.R
import com.unilever.julia.components.TagsModel
import com.unilever.julia.components.boletim.AttendanceModel
import com.unilever.julia.components.model.BoletimChatModel
import com.unilever.julia.components.boletim.BoletimFiltros
import com.unilever.julia.data.model.bulletin.BulletinsMultipleFiltersResponse
import com.unilever.julia.components.boletim.AttendanceFilter
import com.unilever.julia.ui.base.*
import com.unilever.julia.utils.MaterialDialog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.apache.commons.lang3.StringUtils
import java.util.concurrent.TimeUnit

import javax.inject.Inject

class BoletimMainPresenterImpl<V : BoletimMainView, I : BoletimMainInteractor>
@Inject constructor(mView: V, mInteractor: I) : BasePresenterImpl<V, I>(mView, mInteractor), BoletimMainPresenter<V, I> {

    private var mMyTerritory = ""

    override fun init(territory: String, attendanceFilter: AttendanceFilter?, filters: BoletimFiltros?) {
        getView().addDisposable(
            Observable.fromCallable {
                mMyTerritory = territory
                return@fromCallable true
            }
                .delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    getView().showLoadView()
                }
                .subscribeWith(object : DisposableObserver<Boolean>() {
                    override fun onNext(value: Boolean) {
                        if (attendanceFilter != null) {
                            setAttendanceFilter(attendanceFilter)
                        }
                        if (filters != null) {
                            getView().addTags(filters)
                        }
                    }

                    override fun onComplete() {
                        getView().hideLoadView()
                    }

                    override fun onError(e: Throwable) {
                    }
                })
        )
    }

    private var mAttendanceFilter: AttendanceFilter? = null

    override fun updateAreaAtendimento(attendance: AttendanceModel) {
        val tags : TagsModel
        val serviceArea = AttendanceFilter()
        when (attendance.type) {
            AttendanceModel.Type.territory -> {
                serviceArea.territory = attendance.id
                tags = TagsModel(attendance.id, "TER - "+attendance.description)
            }
            AttendanceModel.Type.district -> {
                serviceArea.district = attendance.id
                tags = TagsModel(attendance.id,"DIS - "+attendance.description)
            }
            AttendanceModel.Type.subsidiary -> {
                serviceArea.subsidiary = attendance.id
                tags = TagsModel(attendance.id,"FIL - "+attendance.description)
            }
            AttendanceModel.Type.hitRegional -> {
                serviceArea.hitRegional = attendance.id
                tags = TagsModel(attendance.id,"REG - "+attendance.description)
            }
        }
        mAttendanceFilter = serviceArea

        if (attendance.type == AttendanceModel.Type.territory
            && StringUtils.equalsIgnoreCase(mAttendanceFilter?.territory, mMyTerritory)) {
            getView().clearAttendanceTag()
        } else {
            getView().addAttendanceTag(tags)
        }
    }

    private fun setAttendanceFilter(attendance : AttendanceFilter) {
        mAttendanceFilter = attendance
    }

    private fun getAttendanceFilter(): AttendanceFilter {
        return mAttendanceFilter ?: AttendanceFilter(mMyTerritory)
    }

    override fun navegarAreaAtendimento() {
        getView().redirectAttendanceActivity(mMyTerritory)
    }

    override fun navegarCanais() {
        getView().redirectBoletimMultipleActivity(
            BulletinsMultipleFiltersResponse.Type.CANAIS,
            getView().getTextString(R.string.canais),
            getView().getTextString(R.string.buscar_canais),
            getAttendanceFilter()
        )
    }

    override fun navegarClientes() {
        getView().redirectBoletimMultipleActivity(
            BulletinsMultipleFiltersResponse.Type.CLIENTES,
            getView().getTextString(R.string.clientes),
            getView().getTextString(R.string.buscar_clientes),
            getAttendanceFilter()
        )
    }

    override fun navegarCommodities() {
        getView().redirectBoletimMultipleActivity(
            BulletinsMultipleFiltersResponse.Type.COMMODITIES,
            getView().getTextString(R.string.commodites),
            getView().getTextString(R.string.buscar_commodities),
            getAttendanceFilter()
        )
    }

    override fun navegarCategorias() {
        getView().redirectBoletimMultipleActivity(
            BulletinsMultipleFiltersResponse.Type.CATEGORIAS,
            getView().getTextString(R.string.categorias),
            getView().getTextString(R.string.buscar_categorias),
            getAttendanceFilter()
        )
    }

    override fun navegarMarcas() {
        getView().redirectBoletimMultipleActivity(
            BulletinsMultipleFiltersResponse.Type.MARCAS,
            getView().getTextString(R.string.marcas),
            getView().getTextString(R.string.buscar_marcas),
            getAttendanceFilter()
        )
    }

    override fun applyFilter(filters: BoletimFiltros) {
        getView().addDisposable(
            getInteractor().getBulletinsByFilter(
                getView().getContext(),
                mMyTerritory,
                filters.attendance,
                filters.brand,
                filters.category,
                filters.commodity,
                filters.customer,
                filters.channel
            ).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    getView().showProgressDialog()
                }
                .subscribeWith(object : DisposableObserver<BoletimChatModel>() {
                    override fun onNext(value: BoletimChatModel) {
                        getView().onReturnWithNewData(value, getAttendanceFilter(), filters)
                    }

                    override fun onComplete() {
                        getView().hideProgressDialog()
                    }

                    override fun onError(e: Throwable) {
                        getView().hideProgressDialog()
                        getView().onErrorHandlerDialog(e, MaterialDialog.OnClickListener { dialog -> dialog.dismiss() })
                    }
                })
        )
    }
}