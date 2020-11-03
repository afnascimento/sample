package com.unilever.julia.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class AgendaOutlookParticipante : Parcelable {

    @SerializedName("email")
    var email: String? = null

    constructor(email: String?) {
        this.email = email
    }

    constructor(source: Parcel) : this(
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(email)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<AgendaOutlookParticipante> =
            object : Parcelable.Creator<AgendaOutlookParticipante> {
                override fun createFromParcel(source: Parcel): AgendaOutlookParticipante = AgendaOutlookParticipante(source)
                override fun newArray(size: Int): Array<AgendaOutlookParticipante?> = arrayOfNulls(size)
            }
    }
}