package com.unilever.julia.data.certificate

import android.content.Context
import com.unilever.julia.R
import com.unilever.julia.data.model.Enviroment
import java.io.IOException
import java.io.InputStream
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*

class ICertificateApiImpl(context: Context, certificate : Enviroment.Certificate) : ICertificateApi, X509TrustManager {

    private val keyStorePass = certificate.key
    private val caInput = context.resources.openRawResource(R.raw.truststore)
    private val keystore = readStore(caInput, keyStorePass)
    private val manager = getTrustManager(keystore)
    private val sslContext = sslContextForTrustedCertificate(keystore, keyStorePass)

    private fun readStore(caInput: InputStream, keyStorePass: String): KeyStore {
        try {
            val keyStore = KeyStore.getInstance("PKCS12")//"PKCS12" or "JKS"
            keyStore.load(caInput, keyStorePass.toCharArray())
            return keyStore
        } catch (e: Throwable) {
            throw e
        } finally {
            try {
                caInput.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun getTrustManager(keyStore: KeyStore): X509TrustManager {
        // Create a TrustManager that trusts the CAs in our KeyStore
        val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
        val managerFactory = TrustManagerFactory.getInstance(tmfAlgorithm)
        managerFactory.init(keyStore)

        return managerFactory.trustManagers[0] as X509TrustManager
    }

    private fun sslContextForTrustedCertificate(
        keystore: KeyStore,
        keyStorePass: String
    ): SSLContext {
        try {
            val kmf = KeyManagerFactory.getInstance("X509")
            kmf.init(keystore, keyStorePass.toCharArray())
            val keyManagers = kmf.keyManagers

            val trustManagers = arrayOf<TrustManager>(this)

            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(keyManagers, trustManagers, SecureRandom())
            return sslContext
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getTrustManager(): X509TrustManager {
        return manager
    }

    override fun getSSLSocketFactory(): SSLSocketFactory {
        return sslContext.socketFactory
    }

    override fun checkClientTrusted(certs: Array<out X509Certificate>?, authType: String?) {
        manager.checkClientTrusted(certs, authType)
    }

    override fun checkServerTrusted(certs: Array<out X509Certificate>?, authType: String?) {
        manager.checkServerTrusted(certs, authType)
    }

    override fun getAcceptedIssuers(): Array<X509Certificate> {
        return arrayOf()
    }
}