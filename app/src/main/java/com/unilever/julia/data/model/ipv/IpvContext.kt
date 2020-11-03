package com.unilever.julia.data.model.ipv

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import org.apache.commons.lang3.StringUtils

data class IpvContext(
    @SerializedName("code")
    var code: String? = "",
    @SerializedName("value")
    var value: String? = ""
) : Parcelable {

    fun isManagement(): Boolean {
        return StringUtils.equalsIgnoreCase(code, "14_IPV_LISTA_NIVEL")
    }

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(code)
        parcel.writeString(value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<IpvContext> {
        override fun createFromParcel(parcel: Parcel): IpvContext {
            return IpvContext(parcel)
        }

        override fun newArray(size: Int): Array<IpvContext?> {
            return arrayOfNulls(size)
        }
    }
}