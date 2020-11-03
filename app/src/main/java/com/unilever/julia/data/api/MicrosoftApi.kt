package com.unilever.julia.data.api

import com.unilever.julia.data.model.GraphProfile
import io.reactivex.Observable

interface MicrosoftApi {

    fun callGraphAPI(graphToken: String): Observable<GraphProfile>
}