package com.unilever.julia.ui.ipv

import com.unilever.julia.data.model.ipv.IpvFaseamento
import com.unilever.julia.data.model.ipv.IpvClient
import com.unilever.julia.data.model.ipv.IpvContext
import com.unilever.julia.data.model.ipv.IpvOffers
import com.unilever.julia.ui.base.*
import io.reactivex.Observable

interface IpvInteractor : BaseInteractor {

    fun getIpvFaseamento(sessionCode: String, context: IpvContext): Observable<IpvFaseamento>
    fun getIpvOffers(sessionCode: String, context: IpvContext): Observable<IpvOffers>
    fun getIpvPrioritiesClients(sessionCode: String, context: IpvContext): Observable<List<IpvClient>>
    fun getFocusClients(sessionCode: String, context: IpvContext): Observable<List<IpvClient>>
    fun getPositivationClients(sessionCode: String, context: IpvContext): Observable<List<IpvClient>>
    fun getInnovationClients(sessionCode: String, context: IpvContext): Observable<List<IpvClient>>
}