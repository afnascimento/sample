package com.unilever.julia.data.api

import com.unilever.julia.BuildConfig
import com.unilever.julia.data.model.Enviroment
import com.unilever.julia.data.preferences.PreferencesHelper
import org.apache.commons.lang3.StringUtils

class IJuliaUnileverApiConfigImpl(private val mBroker: Enviroment.Broker,
                                  private val mPreferences: PreferencesHelper) : IJuliaUnileverApiConfig {

    override fun getEmail(): String {
        return mPreferences.getUser()?.mail ?: ""
    }

    override fun getToken(): String {
        val tokenPref = mPreferences.getToken()
        if (tokenPref != null && !tokenPref.isExpired()) {
            return tokenPref.mAccessToken ?: ""
        }
        return ""
    }

    private var mURL : String? = null

    @Suppress("ConstantConditionIf", "SpellCheckingInspection")
    override fun getUrl(): String {
        if (mURL == null) {
            mURL = if (BuildConfig.IS_URL_VERSION) {
                getUrlByEnviroment()
            } else {
                mBroker.url
            }
        }
        return mURL ?: ""
    }

    private fun getUrlByEnviroment(): String {
        val url = mBroker.url
        val prefix = StringUtils.substringBeforeLast(url, "broker") + "broker-"
        val variant = BuildConfig.URL_VERSION
        val sufix = "-bra" + StringUtils.substringAfterLast(url, "-bra")
        return "$prefix$variant$sufix"
    }

    override fun getOS(): String {
        return mBroker.os
    }

    override fun getChannel(): String {
        return mBroker.channel
    }

    override fun getProject(): String {
        return mBroker.project
    }

    override fun getApiKey(): String {
        return mBroker.apiKey
    }

    override fun getAccessControlAllowOrigin(): String {
        return mBroker.accessControlAllowOrigin
    }
}