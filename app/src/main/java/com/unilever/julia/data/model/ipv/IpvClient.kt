package com.unilever.julia.data.model.ipv

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.unilever.julia.utils.Utils
import org.apache.commons.lang3.StringUtils
import java.lang.StringBuilder

data class IpvClient(
    @SerializedName("colorCode")
    var colorCode: String? = "",
    @SerializedName("commodity")
    var commodity: String? = "",
    @SerializedName("context")
    var context: IpvContext? = IpvContext(),
    @SerializedName("customerCity")
    var customerCity: String? = "",
    @SerializedName("customerCode")
    var customerCode: String? = "",
    @SerializedName("customerName")
    var customerName: String? = "",
    @SerializedName("customerRegion")
    var customerRegion: String? = "",
    @SerializedName("customerState")
    var customerState: String? = "",
    @SerializedName("score")
    var score: Double? = 0.0
) : Parcelable {

    fun getTextFilter(): String {
        return getNameWithCode() +" "+ getAddress()
    }

    fun getAddress(): String {
        val mState : String = customerState ?: ""
        val mCity : String = customerCity ?: ""
        val mDistrict : String = customerRegion ?: ""
        //val mStreet : String = street ?: ""
        return "$mCity - $mState - $mDistrict"
    }

    fun getNameWithCode(): String {
        val codeString : String = customerCode ?: ""
        val nameString : String = customerName ?: ""
        return  "$codeString - $nameString"
    }

    fun getScoreText(): String {
        return Utils.getTextPercent(score ?: 0.0)
    }

    fun getCommodityText() : String {
        val temp = StringUtils.trimToEmpty(commodity)
        if (StringUtils.length(temp) >= 4) {
            return if (StringUtils.contains(temp, "-")) {
                val split = StringUtils.split(temp, "-")
                val builder = StringBuilder()
                builder.append(StringUtils.trim(split[0]))
                builder.append("\n")
                builder.append(StringUtils.trim(split[1]))
                builder.toString()
            } else {
                val builder = StringBuilder()
                builder.append(temp.substring(0, 2))
                builder.append("\n")
                builder.append(StringUtils.trim(temp.substring(2)))
                builder.toString()
            }
        }
        return temp
    }

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(IpvContext::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(colorCode)
        parcel.writeString(commodity)
        parcel.writeParcelable(context, flags)
        parcel.writeString(customerCity)
        parcel.writeString(customerCode)
        parcel.writeString(customerName)
        parcel.writeString(customerRegion)
        parcel.writeString(customerState)
        parcel.writeValue(score)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<IpvClient> {
        override fun createFromParcel(parcel: Parcel): IpvClient {
            return IpvClient(parcel)
        }

        override fun newArray(size: Int): Array<IpvClient?> {
            return arrayOfNulls(size)
        }
    }
}