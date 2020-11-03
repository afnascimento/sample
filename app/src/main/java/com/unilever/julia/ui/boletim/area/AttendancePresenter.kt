package com.unilever.julia.ui.boletim.area

import com.unilever.julia.components.boletim.AttendanceModel
import com.unilever.julia.components.boletim.AttendanceRecyclerFilter
import com.unilever.julia.components.boletim.AttendancesModel
import com.unilever.julia.ui.base.*

interface AttendancePresenter<V : AttendanceView, I : AttendanceInteractor> : BasePresenter<V, I>,
    AttendanceRecyclerFilter.Listener {

    fun init(myTerritory : String)
    fun getData(): AttendancesModel?
    fun getSelected(): AttendanceModel
    fun applyAttendance()
}