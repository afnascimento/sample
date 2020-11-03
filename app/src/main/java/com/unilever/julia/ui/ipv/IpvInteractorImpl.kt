package com.unilever.julia.ui.ipv

import com.google.gson.Gson
import com.unilever.julia.ui.base.*

import com.unilever.julia.data.*
import com.unilever.julia.data.api.JuliaIntent
import com.unilever.julia.data.model.ipv.*
import io.reactivex.Observable

import javax.inject.Inject

class IpvInteractorImpl
@Inject constructor(mDataManager: DataManager) : BaseInteractorImpl(mDataManager), IpvInteractor {

    private val mGson = Gson()

    override fun getIpvOffers(sessionCode: String, context: IpvContext): Observable<IpvOffers> {
        return dataManager().juliaApi().getIpvConversations(
            sessionCode, JuliaIntent.Intent.IPV_OFERTAS.value, context.value ?: ""
        ).flatMap { conversations ->
                val model = mGson.fromJson<IpvOffers>(
                    conversations.getAnswer().technicalText,
                    IpvOffers::class.java
                )
                return@flatMap Observable.just(model)
            }
    }

    override fun getIpvFaseamento(sessionCode: String, context: IpvContext): Observable<IpvFaseamento> {
        return dataManager().juliaApi().getIpvConversations(
            sessionCode, JuliaIntent.Intent.IPV_FASEAMENTO.value, context.value ?: ""
        ).flatMap { conversations ->
                val model = mGson.fromJson<IpvFaseamento>(
                    conversations.getAnswer().technicalText,
                    IpvFaseamento::class.java
                )
                return@flatMap Observable.just(model)
            }
    }

    override fun getIpvPrioritiesClients(sessionCode: String, context: IpvContext): Observable<List<IpvClient>> {
        return dataManager().juliaApi().getIpvConversations(
            sessionCode, JuliaIntent.Intent.IPV_PRIORITARIOS_CLIENTES.value, context.value ?: ""
        ).flatMap { conversations ->
                val model = mGson.fromJson<IpvPriorities>(
                    conversations.getAnswer().technicalText,
                    IpvPriorities::class.java
                )
                return@flatMap Observable.just(model.clients)
            }
    }

    override fun getFocusClients(sessionCode: String, context: IpvContext): Observable<List<IpvClient>> {
        return dataManager().juliaApi().getIpvConversations(
            sessionCode, JuliaIntent.Intent.IPV_FOCO_CLIENTES.value, context.value ?: ""
        ).flatMap { conversations ->
                val model = mGson.fromJson<IpvFocus>(
                    conversations.getAnswer().technicalText,
                    IpvFocus::class.java
                )
                return@flatMap Observable.just(model.clients)
            }
    }

    override fun getPositivationClients(sessionCode: String, context: IpvContext): Observable<List<IpvClient>> {
        return dataManager().juliaApi().getIpvConversations(
            sessionCode, JuliaIntent.Intent.IPV_POSITIVACAO.value, context.value ?: ""
        ).flatMap { conversations ->
                val model = mGson.fromJson<IpvPositivation>(
                    conversations.getAnswer().technicalText,
                    IpvPositivation::class.java
                )
                return@flatMap Observable.just(model.clients)
            }
    }

    override fun getInnovationClients(sessionCode: String, context: IpvContext): Observable<List<IpvClient>> {
        return dataManager().juliaApi().getIpvConversations(
            sessionCode, JuliaIntent.Intent.IPV_INOVACAO.value, context.value ?: ""
        ).flatMap { conversations ->
                val model = mGson.fromJson<IpvInnovation>(
                    conversations.getAnswer().technicalText,
                    IpvInnovation::class.java
                )
                return@flatMap Observable.just(model.clients)
            }
    }
}