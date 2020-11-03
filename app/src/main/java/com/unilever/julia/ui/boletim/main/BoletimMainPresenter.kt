package com.unilever.julia.ui.boletim.main

import com.unilever.julia.components.boletim.AttendanceFilter
import com.unilever.julia.components.boletim.AttendanceModel
import com.unilever.julia.components.boletim.BoletimFiltros
import com.unilever.julia.ui.base.*

interface BoletimMainPresenter<V : BoletimMainView, I : BoletimMainInteractor> : BasePresenter<V, I> {
    fun navegarCanais()
    fun updateAreaAtendimento(attendance: AttendanceModel)
    fun init(territory: String, attendanceFilter: AttendanceFilter?, filters: BoletimFiltros?)
    fun navegarClientes()
    fun navegarCommodities()
    fun navegarCategorias()
    fun navegarMarcas()
    fun navegarAreaAtendimento()
    fun applyFilter(filters: BoletimFiltros)
}