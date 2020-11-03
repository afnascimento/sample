package com.unilever.julia.ui.boletim.multiple

import com.unilever.julia.data.model.bulletin.BulletinsMultipleFiltersResponse
import com.unilever.julia.components.boletim.AttendanceFilter
import com.unilever.julia.ui.base.*
import io.reactivex.Observable

interface BoletimMultipleInteractor : BaseInteractor {

    fun getBulletinsMultipleFilters(
        type: BulletinsMultipleFiltersResponse.Type,
        attendanceFilter: AttendanceFilter
    ): Observable<List<String>>
}