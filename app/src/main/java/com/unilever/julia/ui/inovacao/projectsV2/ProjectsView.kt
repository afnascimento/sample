package com.unilever.julia.ui.inovacao.projectsV2

import com.unilever.julia.data.model.InnovationProjectsModel
import com.unilever.julia.ui.base.*

interface ProjectsView : BaseView {

    fun initTabs(projects: MutableList<InnovationProjectsModel.Project>)
}