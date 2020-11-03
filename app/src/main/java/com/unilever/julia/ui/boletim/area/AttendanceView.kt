package com.unilever.julia.ui.boletim.area

import com.unilever.julia.components.boletim.AttendanceModel
import com.unilever.julia.ui.base.*

interface AttendanceView : BaseView {

    fun initTabs()
    fun updateSelectedAllFragments(attendance: AttendanceModel)
    fun onReturnWithResult(attendance: AttendanceModel)
}