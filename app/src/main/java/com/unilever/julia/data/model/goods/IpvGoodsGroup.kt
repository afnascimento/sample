package com.unilever.julia.data.model.goods

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.unilever.julia.components.model.IpvRecylerItem
import com.unilever.julia.data.model.ipv.IpvContext

class IpvGoodsGroup : Parcelable, IpvRecylerItem {

    @SerializedName("colorCode")
    var colorCode: String? = ""
    @SerializedName("context")
    var context: IpvContext? = IpvContext()
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
        return ""
    }

    override fun isTextRegularVisible(): Boolean {
        return false
    }

    override fun getTextBold(): String {
        return description ?: ""
    }

    override fun isTextBoldVisible(): Boolean {
        return true
    }

    override fun isButtonClickVisible(): Boolean {
        return true
    }

    override fun getTextFilter(): String {
        return description ?: ""
    }

    constructor(colorCode: String?, context: IpvContext?, description: String?, icon: String?) {
        this.colorCode = colorCode
        this.context = context
        this.description = description
        this.icon = icon
    }

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(IpvContext::class.java.classLoader),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(colorCode)
        parcel.writeParcelable(context, flags)
        parcel.writeString(description)
        parcel.writeString(icon)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<IpvGoodsGroup> {
        override fun createFromParcel(parcel: Parcel): IpvGoodsGroup {
            return IpvGoodsGroup(parcel)
        }

        override fun newArray(size: Int): Array<IpvGoodsGroup?> {
            return arrayOfNulls(size)
        }
    }
}