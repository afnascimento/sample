package com.unilever.julia

import android.content.Context
import com.unilever.julia.data.certificate.ICertificateApi
import com.unilever.julia.data.certificate.ICertificateApiImpl
import com.unilever.julia.data.model.Enviroment

class FlavorConfig {

    companion object {
        fun getCertificateApi(context: Context, certificate : Enviroment.Certificate): ICertificateApi? {
            return ICertificateApiImpl(context, certificate)
        }
    }
}