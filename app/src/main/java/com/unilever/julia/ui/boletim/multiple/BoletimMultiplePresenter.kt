package com.unilever.julia.ui.boletim.multiple

import com.unilever.julia.data.model.bulletin.BulletinsMultipleFiltersResponse
import com.unilever.julia.components.boletim.AttendanceFilter
import com.unilever.julia.ui.base.*

interface BoletimMultiplePresenter<V : BoletimMultipleView, I : BoletimMultipleInteractor> :
    BasePresenter<V, I> {

    fun init(type: BulletinsMultipleFiltersResponse.Type, attendanceFilter: AttendanceFilter)
}