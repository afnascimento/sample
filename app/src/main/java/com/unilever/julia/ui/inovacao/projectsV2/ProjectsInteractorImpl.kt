package com.unilever.julia.ui.inovacao.projectsV2

import com.google.gson.GsonBuilder
import com.unilever.julia.ui.base.*

import com.unilever.julia.data.*
import com.unilever.julia.data.model.Conversations
import com.unilever.julia.data.model.InnovationProjectsModel
import com.unilever.julia.exception.EmptyDataException
import io.reactivex.Observable
import io.reactivex.functions.Function
import java.util.concurrent.TimeUnit

import javax.inject.Inject

class ProjectsInteractorImpl
@Inject constructor(mDataManager: DataManager) : BaseInteractorImpl(mDataManager), ProjectsInteractor {

    override fun getProjectsInnovations(sessionCode: String, code: String, marcaId: String, categoriaId: String, commodityId: String): Observable<MutableList<InnovationProjectsModel.Project>> {
        return juliaUnileverApi().getProjectsInnovationsV2(sessionCode, code, categoriaId, commodityId, marcaId)
            .delay(1, TimeUnit.SECONDS)
            .flatMap(Function<Conversations, Observable<MutableList<InnovationProjectsModel.Project>>> {
                conversations: Conversations ->

                val gson = GsonBuilder()
                    .registerTypeAdapter(
                        InnovationProjectsModel.Item::class.java,
                        InnovationProjectsModel.Deserializer(sessionCode, conversations.getNextNode())
                    )
                    .create()

                val json : String = conversations.getAnswer().technicalText ?: ""
                val model = gson.fromJson(json, InnovationProjectsModel::class.java)

                if (model.projects.isNullOrEmpty()) {
                    throw EmptyDataException()
                }

                return@Function Observable.just(model.projects)
            })
    }
}