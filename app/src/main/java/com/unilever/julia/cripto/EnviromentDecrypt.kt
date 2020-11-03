package com.unilever.julia.cripto

import android.content.Context
import androidx.annotation.RawRes
import com.google.gson.Gson
import com.unilever.julia.BuildConfig
import com.unilever.julia.R
import com.unilever.julia.data.model.Enviroment
import com.unilever.julia.logger.Logger
import org.apache.commons.io.IOUtils
import java.io.InputStream
import java.nio.charset.StandardCharsets

class EnviromentDecrypt {

    private val charset = StandardCharsets.UTF_8

    private fun getStream(context : Context, @RawRes resId: Int): InputStream {
        return context.resources.openRawResource(resId)
    }

    private fun getStreamData(context : Context, @RawRes resId: Int): String {
        return IOUtils.toString(getStream(context, resId), charset)
    }

    fun getEnviroment(context: Context): Enviroment {
        val aesCrypt = AESCrypt(AndroidBase64())

        val flavor = BuildConfig.FLAVOR
        var encrypt = ""
        when (flavor) {
            "dev" -> encrypt = getStreamData(context, R.raw.enviroment_dev)
            "appScan" -> encrypt = getStreamData(context, R.raw.enviroment_appscan)
            "preProd" -> encrypt = getStreamData(context, R.raw.enviroment_preprod)
            "prod" -> encrypt = getStreamData(context, R.raw.enviroment_prod)
        }
        Logger.debug("FINGERPRINT", encrypt)

        val decrypt = aesCrypt.decrypt(encrypt)
        Logger.debug("FINGERPRINT", decrypt)

        val enviroment = Gson().fromJson(decrypt, Enviroment::class.java)
        Logger.debug("FINGERPRINT", enviroment.toString())

        return enviroment
    }
}