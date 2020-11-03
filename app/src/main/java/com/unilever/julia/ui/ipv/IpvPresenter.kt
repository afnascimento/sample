package com.unilever.julia.ui.ipv

import com.unilever.julia.data.model.ipv.*
import com.unilever.julia.ui.base.*
import io.reactivex.Observable

interface IpvPresenter<V : IpvView, I : IpvInteractor> : BasePresenter<V, I> {

    fun getIpvFaseamento(sessionCode: String): Observable<IpvFaseamento>
    fun getIpvPrioritiesClients(sessionCode: String): Observable<List<IpvClient>>
    fun init(context: IpvContext, items: List<IpvItem>)
    fun getIpvModel(type: IpvType): IpvItem?
    fun getFocusClients(sessionCode: String): Observable<List<IpvClient>>
    fun onClientFocusClick(ipvClient: IpvClient)
    fun onClientPrioritiesClick(ipvClient: IpvClient)
    fun getIpvOffers(sessionCode: String): Observable<IpvOffers>
    fun getPositivationClients(sessionCode: String): Observable<List<IpvClient>>
    fun getInnovationClients(sessionCode: String): Observable<List<IpvClient>>
    fun onClientInnovationClick(ipvClient: IpvClient)
}