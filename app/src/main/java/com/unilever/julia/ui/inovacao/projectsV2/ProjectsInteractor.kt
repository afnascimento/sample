package com.unilever.julia.ui.inovacao.projectsV2

import com.unilever.julia.data.model.InnovationProjectsModel
import com.unilever.julia.ui.base.*
import io.reactivex.Observable

interface ProjectsInteractor : BaseInteractor {

    fun getProjectsInnovations(
        sessionCode: String,
        code: String,
        marcaId: String,
        categoriaId: String,
        commodityId: String
    ): Observable<MutableList<InnovationProjectsModel.Project>>
}