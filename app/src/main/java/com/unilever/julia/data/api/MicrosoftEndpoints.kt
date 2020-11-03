package com.unilever.julia.data.api

import com.unilever.julia.data.model.GraphProfile
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.HeaderMap

interface MicrosoftEndpoints {

    @GET("/v1.0/me")
    fun callGraphAPI(@HeaderMap headers: MutableMap<String, String>): Observable<GraphProfile>
}