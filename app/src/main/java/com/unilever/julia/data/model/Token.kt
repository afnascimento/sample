package com.unilever.julia.data.model

import com.google.gson.annotations.SerializedName
import org.apache.commons.lang3.StringUtils
import java.util.*

class Token {

    @SerializedName("AccessToken") var mAccessToken : String? = null
    @SerializedName("ExpiresOn") var mExpiresOn : Date? = null
    @SerializedName("Scopes") var mScopes : Array<String>? = null

    private fun isValid(): Boolean {
        if (StringUtils.isNotBlank(mAccessToken) && mExpiresOn != null && !mScopes.isNullOrEmpty()) {
            return true
        }
        return false
    }

    fun isExpired(): Boolean {
        if (!isValid()) {
            return true
        }
        // Init a Calendar for the current time/date
        val calendar = Calendar.getInstance()
        val validity = calendar.time
        // Init a Date for the accessToken's expiry
        //val epoch = java.lang.Long.valueOf(expires)
        //val expiresOn = Date(
        //    TimeUnit.SECONDS.toMillis(epoch)
        //)
        return mExpiresOn?.before(validity) ?: true
    }

    override fun toString(): String {
        return "Token(AccessToken=$mAccessToken, \nExpiresOn=$mExpiresOn, \nScopes=${Arrays.toString(mScopes)})"
    }
}