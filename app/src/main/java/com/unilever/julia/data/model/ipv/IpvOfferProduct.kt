package com.unilever.julia.data.model.ipv

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.unilever.julia.utils.Utils
import org.apache.commons.lang3.StringUtils

data class IpvOfferProduct(
    @SerializedName("code")
    var code: String? = "",
    @SerializedName("colorCode")
    var colorCode: String? = "",
    @SerializedName("description")
    var description: String? = "",
    @SerializedName("entry")
    var entry: Double? = 0.0,
    @SerializedName("score")
    var score: Double? = 0.0,
    @SerializedName("target")
    var target: Double? = 0.0
) : Parcelable {

    fun getTextFilter(): String {
        return StringUtils.trimToEmpty(code) +" "+ StringUtils.trimToEmpty(description)
    }

    fun getScoreRound(): Double {
        return Utils.roundDecimal(score ?: 0.0, 2)
    }

    fun getTextScore(): String {
        return Utils.getTextPercent(getScoreRound())
    }

    fun getTextEntry(): String {
        val roundValue = Utils.roundDecimal(entry ?: 0.0, 2)
        return roundValue.toString()
    }

    fun getTextTarget(): String {
        val value = Utils.roundDecimal(target ?: 0.0, 2)
        return value.toString()
    }

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(code)
        parcel.writeString(colorCode)
        parcel.writeString(description)
        parcel.writeValue(entry)
        parcel.writeValue(score)
        parcel.writeValue(target)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<IpvOfferProduct> {
        override fun createFromParcel(parcel: Parcel): IpvOfferProduct {
            return IpvOfferProduct(parcel)
        }

        override fun newArray(size: Int): Array<IpvOfferProduct?> {
            return arrayOfNulls(size)
        }
    }
}