package com.unilever.julia.ui.boletim.main

import com.unilever.julia.components.TagsModel
import com.unilever.julia.components.model.BoletimChatModel
import com.unilever.julia.components.boletim.BoletimFiltros
import com.unilever.julia.data.model.bulletin.BulletinsMultipleFiltersResponse
import com.unilever.julia.components.boletim.AttendanceFilter
import com.unilever.julia.ui.base.*

interface BoletimMainView : BaseView {

    fun redirectBoletimMultipleActivity(
        type: BulletinsMultipleFiltersResponse.Type,
        toolbarText: String,
        hintText: String,
        attendanceFilter: AttendanceFilter
    )

    fun onReturnWithNewData(model: BoletimChatModel, attendanceFilter: AttendanceFilter, filters: BoletimFiltros)
    fun addTags(filters: BoletimFiltros)
    fun redirectAttendanceActivity(myTerritory: String)
    fun addAttendanceTag(tag: TagsModel)
    fun clearAttendanceTag()
}