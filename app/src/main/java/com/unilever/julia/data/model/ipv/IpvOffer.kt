package com.unilever.julia.data.model.ipv

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class IpvOffer(
    @SerializedName("commodity")
    var commodity: String? = "",
    @SerializedName("context")
    var context: IpvContext? = IpvContext(),
    @SerializedName("icon")
    var icon: String? = ""
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(IpvContext::class.java.classLoader),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(commodity)
        parcel.writeParcelable(context, flags)
        parcel.writeString(icon)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<IpvOffer> {
        override fun createFromParcel(parcel: Parcel): IpvOffer {
            return IpvOffer(parcel)
        }

        override fun newArray(size: Int): Array<IpvOffer?> {
            return arrayOfNulls(size)
        }
    }
}