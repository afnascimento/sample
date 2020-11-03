package com.unilever.julia.data.certificate

import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

interface ICertificateApi {

    fun getSSLSocketFactory(): SSLSocketFactory

    fun getTrustManager(): X509TrustManager
}