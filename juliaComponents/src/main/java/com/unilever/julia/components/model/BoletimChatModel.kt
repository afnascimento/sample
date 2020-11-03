package com.unilever.julia.components.model

import android.os.Parcel
import android.os.Parcelable
import com.unilever.julia.components.enums.ComponentsType

data class BoletimChatModel(
    var text : String = "",
    var card: BoletimCard? = null
) : IComponentsModel, Parcelable {

    override fun getType(): ComponentsType {
        return ComponentsType.BOLETIM_CARD_CHAT
    }

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readParcelable(BoletimCard::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(text)
        parcel.writeParcelable(card, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BoletimChatModel> {
        override fun createFromParcel(parcel: Parcel): BoletimChatModel {
            return BoletimChatModel(parcel)
        }

        override fun newArray(size: Int): Array<BoletimChatModel?> {
            return arrayOfNulls(size)
        }
    }
}