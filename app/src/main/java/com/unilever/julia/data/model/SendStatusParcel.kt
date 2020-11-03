package com.unilever.julia.data.model

import android.os.Parcel
import android.os.Parcelable

class SendStatusParcel: Parcelable {

    var sessionCode: String = ""
    var code: String = ""
    var customerId: String = ""
    var orderId: String = ""
    var statusPedido: String = ""
    var idRegion: String = ""

    constructor()

    constructor(source: Parcel) : this(
        source.readString() ?: "",
        source.readString()?: "",
        source.readString()?: "",
        source.readString()?: "",
        source.readString()?: "",
        source.readString()?: ""
    )

    constructor(
        sessionCode: String,
        code: String,
        customerId: String,
        orderId: String,
        statusPedido: String,
        idRegion: String
    ) {
        this.sessionCode = sessionCode
        this.code = code
        this.customerId = customerId
        this.orderId = orderId
        this.statusPedido = statusPedido
        this.idRegion = idRegion
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(sessionCode)
        writeString(code)
        writeString(customerId)
        writeString(orderId)
        writeString(statusPedido)
        writeString(idRegion)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<SendStatusParcel> = object : Parcelable.Creator<SendStatusParcel> {
            override fun createFromParcel(source: Parcel): SendStatusParcel = SendStatusParcel(source)
            override fun newArray(size: Int): Array<SendStatusParcel?> = arrayOfNulls(size)
        }
    }
}
