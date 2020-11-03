package com.unilever.julia.ui.inovacao.projectsV2

import com.google.gson.GsonBuilder
import com.unilever.julia.ui.base.*

import com.unilever.julia.data.*
import com.unilever.julia.data.model.InnovationProjectsModel
import com.unilever.julia.exception.EmptyDataException
import com.unilever.julia.firebase.database.DatabaseRealtime
import com.unilever.julia.firebase.exception.NotFoundDataException
import io.reactivex.Observable
import io.reactivex.functions.Function
import java.util.concurrent.TimeUnit

import javax.inject.Inject

class ProjectsInteractorFirebase
@Inject constructor(mDataManager: DataManager) : BaseInteractorImpl(mDataManager), ProjectsInteractor {

    private val mGson = GsonBuilder()
        .registerTypeAdapter(
            InnovationProjectsModel.Item::class.java,
            InnovationProjectsModel.Deserializer("", "04_INOVACAO_DETALHE")
        )
        .create()

    override fun getProjectsInnovations(sessionCode: String, code: String, marcaId: String, categoriaId: String, commodityId: String): Observable<MutableList<InnovationProjectsModel.Project>> {
        return DatabaseRealtime.getObservableValue(mGson, "inovacoes_tela2", InnovationProjectsModel::class.java)
            .delay(1, TimeUnit.SECONDS)
            .onErrorResumeNext(object : Function<Throwable, Observable<InnovationProjectsModel>> {
                override fun apply(error: Throwable): Observable<InnovationProjectsModel> {
                    if (error is NotFoundDataException) {
                        return Observable.error(EmptyDataException())
                    }
                    return Observable.just(InnovationProjectsModel())
                }
            })
            .flatMap(Function<InnovationProjectsModel, Observable<MutableList<InnovationProjectsModel.Project>>> { value ->
                if (value.projects.isNullOrEmpty()) {
                    throw EmptyDataException()
                }
                return@Function Observable.just(value.projects)
            })
    }
}