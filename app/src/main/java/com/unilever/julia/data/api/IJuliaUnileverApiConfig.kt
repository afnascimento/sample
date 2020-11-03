package com.unilever.julia.data.api

interface IJuliaUnileverApiConfig {

    fun getEmail(): String
    fun getToken(): String
    fun getUrl(): String
    fun getOS(): String
    fun getChannel(): String
    fun getProject(): String
    fun getApiKey(): String
    fun getAccessControlAllowOrigin(): String
}