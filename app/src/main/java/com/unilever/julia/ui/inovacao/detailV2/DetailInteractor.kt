package com.unilever.julia.ui.inovacao.detailV2

import com.unilever.julia.data.model.inovacaoDetalhe.InovacaoDetalhe
import com.unilever.julia.ui.base.*
import io.reactivex.Observable

interface DetailInteractor : BaseInteractor {

    fun getDetail(sessionCode: String, code: String, identifier: String): Observable<InovacaoDetalhe>
    fun putDownloadTradestory(
        sessionCode: String,
        code: String,
        identifier: String
    ): Observable<InovacaoDetalhe>
}