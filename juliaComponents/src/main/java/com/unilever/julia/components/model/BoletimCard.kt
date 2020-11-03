package com.unilever.julia.components.model

import android.os.Parcel
import android.os.Parcelable

data class BoletimCard(
    var territory: String = "",
    var nivel: BoletimItem? = null,
    var tonelada: BoletimItem? = null
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readParcelable(BoletimItem::class.java.classLoader),
        parcel.readParcelable(BoletimItem::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(territory)
        parcel.writeParcelable(nivel, flags)
        parcel.writeParcelable(tonelada, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BoletimCard> {
        override fun createFromParcel(parcel: Parcel): BoletimCard {
            return BoletimCard(parcel)
        }

        override fun newArray(size: Int): Array<BoletimCard?> {
            return arrayOfNulls(size)
        }
    }
}