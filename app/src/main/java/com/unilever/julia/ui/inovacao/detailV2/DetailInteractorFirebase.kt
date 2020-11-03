package com.unilever.julia.ui.inovacao.detailV2

import com.google.gson.Gson
import com.unilever.julia.ui.base.*

import com.unilever.julia.data.*
import com.unilever.julia.data.model.inovacaoDetalhe.InovacaoDetalhe
import com.unilever.julia.exception.EmptyDataException
import com.unilever.julia.firebase.database.DatabaseRealtime
import com.unilever.julia.firebase.exception.NotFoundDataException
import io.reactivex.Observable
import io.reactivex.functions.Function
import java.util.concurrent.TimeUnit

import javax.inject.Inject

class DetailInteractorFirebase
@Inject constructor(mDataManager: DataManager) : BaseInteractorImpl(mDataManager), DetailInteractor {

    private val mGson = Gson()

    override fun getDetail(sessionCode: String, code: String, identifier: String): Observable<InovacaoDetalhe> {
        return DatabaseRealtime.getObservableValue(mGson, "inovacoes_tela3", InovacaoDetalhe::class.java)
            .delay(1, TimeUnit.SECONDS)
            .onErrorResumeNext(object : Function<Throwable, Observable<InovacaoDetalhe>> {
                override fun apply(error: Throwable): Observable<InovacaoDetalhe> {
                    if (error is NotFoundDataException) {
                        return Observable.error(EmptyDataException())
                    }
                    return Observable.just(InovacaoDetalhe())
                }
            })
            .flatMap(Function<InovacaoDetalhe, Observable<InovacaoDetalhe>> { value ->
                return@Function Observable.just(value)
            })
    }

    override fun putDownloadTradestory(
        sessionCode: String,
        code: String,
        identifier: String
    ): Observable<InovacaoDetalhe> {
        return getDetail(sessionCode, code, identifier)
    }
}