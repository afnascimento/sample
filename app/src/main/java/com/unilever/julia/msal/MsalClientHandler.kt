package com.unilever.julia.msal

import android.content.Context
import com.unilever.julia.BuildConfig
import com.unilever.julia.R
import com.unilever.julia.utils.JavaUtils
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.StringUtils
import java.io.File
import java.io.InputStream
import java.nio.charset.StandardCharsets

class MsalClientHandler(private var mContext: Context): IMsalClientHandler {

    private val charset = StandardCharsets.UTF_8

    private val mFilename = "auth_config.json"

    private val mApplicationId = StringUtils.replace(BuildConfig.MSAL_APPLICATION_ID, "msal", "")

    private val mScopes = StringUtils.split(BuildConfig.MSAL_SCOPES, ";")

    private val mFile = getMsalConfig()

    private fun getData() : String {
        val inputStream : InputStream = mContext.resources.openRawResource(R.raw.julia_auth_config)
        val content = IOUtils.toString(inputStream, charset)

        val map : MutableMap<String, Any> = JavaUtils.parseJsonToMapObject(content)

        map["client_id"] = mApplicationId

        val split = StringUtils.split(map["redirect_uri"].toString(), ":")
        val redirectUri = "${split[0]}$mApplicationId:${split[1]}"
        map["redirect_uri"] = redirectUri
        return JavaUtils.toJson(map)
    }

    private fun getMsalConfig(): File {
        try {
            val directory = mContext.getDir("msal", Context.MODE_PRIVATE)
            val file = File(directory, mFilename)
            val json = getData()
            FileUtils.write(file, json, charset)
            return file
        } catch (e : Throwable) {
            throw e
        }
    }

    override fun msalApplicationId(): String {
        return mApplicationId
    }

    override fun msalScopes(): Array<String> {
        return mScopes
    }

    override fun msalFileConfig(): File {
        return mFile
    }
}