package com.unilever.julia.ui.boletim.area

import com.unilever.julia.components.boletim.AttendancesModel
import com.unilever.julia.ui.base.*
import io.reactivex.Observable

interface AttendanceInteractor : BaseInteractor {

    fun getAttendance(myTerritory : String): Observable<AttendancesModel>
}