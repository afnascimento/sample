package com.unilever.julia.components.model

import android.os.Parcel
import android.os.Parcelable

data class BoletimItem(
    var header: Header = Header(),
    var entradaDia : Percentage? = null,
    var entrada : Percentage? = null,
    var faturado : Percentage? = null
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Header::class.java.classLoader) ?: Header(),
        parcel.readParcelable(Percentage::class.java.classLoader),
        parcel.readParcelable(Percentage::class.java.classLoader),
        parcel.readParcelable(Percentage::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(header, flags)
        parcel.writeParcelable(entradaDia, flags)
        parcel.writeParcelable(entrada, flags)
        parcel.writeParcelable(faturado, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BoletimItem> {
        override fun createFromParcel(parcel: Parcel): BoletimItem {
            return BoletimItem(parcel)
        }

        override fun newArray(size: Int): Array<BoletimItem?> {
            return arrayOfNulls(size)
        }
    }

    data class Header(
        var title: String = "",
        var description: String = "",
        var value: Double = 0.0,
        var percentage: Double = 0.0
    ) : Parcelable {

        constructor(parcel: Parcel) : this(
            parcel.readString() ?: "",
            parcel.readString() ?: "",
            parcel.readDouble(),
            parcel.readDouble()
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(title)
            parcel.writeString(description)
            parcel.writeDouble(value)
            parcel.writeDouble(percentage)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Header> {
            override fun createFromParcel(parcel: Parcel): Header {
                return Header(
                    parcel
                )
            }

            override fun newArray(size: Int): Array<Header?> {
                return arrayOfNulls(size)
            }
        }
    }

    data class Percentage(
        var title: String = "",
        var description: String = "",
        var value: Double = 0.0,
        var percentage: Double = 0.0,
        var color: String = ""
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString() ?: "",
            parcel.readString() ?: "",
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.readString() ?: ""
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(title)
            parcel.writeString(description)
            parcel.writeDouble(value)
            parcel.writeDouble(percentage)
            parcel.writeString(color)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Percentage> {
            override fun createFromParcel(parcel: Parcel): Percentage {
                return Percentage(
                    parcel
                )
            }

            override fun newArray(size: Int): Array<Percentage?> {
                return arrayOfNulls(size)
            }
        }
    }
}