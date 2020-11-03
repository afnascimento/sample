package com.unilever.julia.ui.inovacao.projectsV2

import com.unilever.julia.data.model.InnovationProjectsModel
import com.unilever.julia.ui.base.*

interface ProjectsPresenter<V : ProjectsView, I : ProjectsInteractor> : BasePresenter<V, I> {

    fun getProjectsInnovations(
        sessionCode: String,
        code: String,
        marcaId: String,
        categoriaId: String,
        commodityId: String
    )

    fun getProducts(index: Int): MutableList<InnovationProjectsModel.Item>
    fun sortedProjectsByDate(items: MutableList<InnovationProjectsModel.Project>): MutableList<InnovationProjectsModel.Project>
}