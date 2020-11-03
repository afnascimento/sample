package com.unilever.julia.ui.ipv

import com.google.gson.Gson
import com.unilever.julia.ui.base.*

import com.unilever.julia.data.*
import com.unilever.julia.data.model.ipv.IpvFaseamento
import com.unilever.julia.data.model.ipv.IpvClient
import com.unilever.julia.data.model.ipv.IpvContext
import com.unilever.julia.data.model.ipv.IpvOffers
import com.unilever.julia.exception.EmptyDataException
import io.reactivex.Observable
import com.unilever.julia.firebase.database.DatabaseRealtime
import com.unilever.julia.firebase.exception.NotFoundDataException
import java.util.concurrent.TimeUnit

import javax.inject.Inject

class IpvInteractorFirebase
@Inject constructor(mDataManager: DataManager) : BaseInteractorImpl(mDataManager), IpvInteractor {

    private val mGson = Gson()

    override fun getIpvFaseamento(sessionCode: String, context: IpvContext): Observable<IpvFaseamento> {
        return DatabaseRealtime.getObservableValue(mGson, "tela_detalhe_ipv", IpvFaseamento::class.java)
            .delay(1, TimeUnit.SECONDS)
            .onErrorResumeNext(object : io.reactivex.functions.Function<Throwable, Observable<IpvFaseamento>> {
                override fun apply(error: Throwable): Observable<IpvFaseamento> {
                    if (error is NotFoundDataException) {
                        return Observable.error(EmptyDataException())
                    }
                    return Observable.just(IpvFaseamento())
                }
            })
            .flatMap { model ->
                return@flatMap Observable.just(model)
            }
    }

    override fun getIpvOffers(sessionCode: String, context: IpvContext): Observable<IpvOffers> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getIpvPrioritiesClients(
        sessionCode: String,
        context: IpvContext
    ): Observable<List<IpvClient>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getFocusClients(
        sessionCode: String,
        context: IpvContext
    ): Observable<List<IpvClient>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPositivationClients(
        sessionCode: String,
        context: IpvContext
    ): Observable<List<IpvClient>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getInnovationClients(
        sessionCode: String,
        context: IpvContext
    ): Observable<List<IpvClient>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}