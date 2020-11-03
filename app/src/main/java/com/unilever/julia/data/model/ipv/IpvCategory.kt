package com.unilever.julia.data.model.ipv

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.unilever.julia.components.model.IpvRecylerItem

class IpvCategory: Parcelable, IpvRecylerItem {

    @SerializedName("name")
    var name: String? = ""
    @SerializedName("icon")
    var icon: String? = ""
    @SerializedName("colorCode")
    var colorCode: String? = ""
    @SerializedName("context")
    var context: IpvContext? = IpvContext()

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
        return name ?: ""
    }

    override fun isTextBoldVisible(): Boolean {
        return true
    }

    override fun isButtonClickVisible(): Boolean {
        return true
    }

    override fun getTextFilter(): String {
        return name ?: ""
    }

    constructor(colorCode: String?, context: IpvContext?, icon: String?, name: String?) {
        this.colorCode = colorCode
        this.context = context
        this.icon = icon
        this.name = name
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
        parcel.writeString(icon)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<IpvCategory> {
        override fun createFromParcel(parcel: Parcel): IpvCategory {
            return IpvCategory(parcel)
        }

        override fun newArray(size: Int): Array<IpvCategory?> {
            return arrayOfNulls(size)
        }
    }
}