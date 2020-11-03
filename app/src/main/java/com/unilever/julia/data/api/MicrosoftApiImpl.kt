package com.unilever.julia.data.api

import com.unilever.julia.data.model.GraphProfile
import com.unilever.julia.logger.Logger
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MicrosoftApiImpl : MicrosoftApi {

    private val mGraphURL = "https://graph.microsoft.com"

    private val mMicrosoftApi : MicrosoftEndpoints

    init {
        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                message -> Logger.debug("MICROSOFT-API", message)
        })
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()

        mMicrosoftApi = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(mGraphURL)
            .client(httpClient)
            .build()
            .create(MicrosoftEndpoints::class.java)
    }

    override fun callGraphAPI(graphToken: String): Observable<GraphProfile> {
        val headers : MutableMap<String, String> = mutableMapOf()
        headers["Authorization"] = "Bearer $graphToken"
        headers["Content-Type"] = "application/json"

        return mMicrosoftApi.callGraphAPI(headers)
    }
}