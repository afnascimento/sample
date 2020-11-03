package com.unilever.julia.data.model.ipv

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import org.apache.commons.lang3.StringUtils

class IpvItem : Parcelable {

    @SerializedName("percentage")
    var percentage : Double? = null

    @SerializedName("colorCode")
    var colorCode : String? = null

    @SerializedName("type")
    var type : String? = null

    fun getIpvType(): IpvType? {
        if (type.isNullOrEmpty()) {
            return null
        }
        val typeCompare = StringUtils.stripAccents(StringUtils.lowerCase(StringUtils.trimToEmpty(type)))
        for (it in  IpvType.values()) {
            if (StringUtils.equalsIgnoreCase(typeCompare, it.value)) {
                return it
            }
        }
        return null
    }

    constructor(parcel: Parcel) : this() {
        percentage = parcel.readValue(Double::class.java.classLoader) as? Double
        colorCode = parcel.readString()
        type = parcel.readString()
    }

    constructor()

    constructor(percentage: Double?, colorCode: String?, type: String?) {
        this.percentage = percentage
        this.colorCode = colorCode
        this.type = type
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(percentage)
        parcel.writeString(colorCode)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR :
        Parcelable.Creator<IpvItem> {
        override fun createFromParcel(parcel: Parcel): IpvItem {
            return IpvItem(parcel)
        }

        override fun newArray(size: Int): Array<IpvItem?> {
            return arrayOfNulls(size)
        }
    }
}