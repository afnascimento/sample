package com.unilever.julia.data.model.goods

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.unilever.julia.components.model.IpvRecylerItem
import java.lang.StringBuilder

class IpvGoodsUnitary : Parcelable, IpvRecylerItem {

    @SerializedName("code")
    var code: String? = ""
    @SerializedName("colorCode")
    var colorCode: String? = ""
    @SerializedName("description")
    var description: String? = ""
    @SerializedName("icon")
    var icon: String? = ""

    override fun getIconCode(): String {
        return icon ?: ""
    }

    override fun getIconColor(): String {
        return colorCode ?: ""
    }

    override fun getTextRegular(): String {
        return code ?: ""
    }

    override fun isTextRegularVisible(): Boolean {
        return true
    }

    override fun getTextBold(): String {
        return description ?: ""
    }

    override fun isTextBoldVisible(): Boolean {
        return true
    }

    override fun isButtonClickVisible(): Boolean {
        return false
    }

    private var textFilter : String? = null

    override fun getTextFilter(): String {
        if (textFilter == null) {
            textFilter = StringBuilder().append(getTextRegular()).append(" ").append(getTextBold()).toString()
        }
        return textFilter ?: ""
    }

    constructor(code: String?, colorCode: String?, description: String?, icon: String?) {
        this.code = code
        this.colorCode = colorCode
        this.description = description
        this.icon = icon
    }

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(code)
        parcel.writeString(colorCode)
        parcel.writeString(description)
        parcel.writeString(icon)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<IpvGoodsUnitary> {
        override fun createFromParcel(parcel: Parcel): IpvGoodsUnitary {
            return IpvGoodsUnitary(parcel)
        }

        override fun newArray(size: Int): Array<IpvGoodsUnitary?> {
            return arrayOfNulls(size)
        }
    }
}