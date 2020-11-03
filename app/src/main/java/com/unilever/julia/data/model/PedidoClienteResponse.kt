package com.unilever.julia.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.unilever.julia.utils.Utils
import java.util.*

class PedidoClienteResponse : Parcelable {

    @SerializedName("numeroPedido")
    var numeroPedido: String? = null

    @SerializedName("quantidade")
    var quantidade: Int? = null

    @SerializedName("valor")
    var valor: Double? = null

    @SerializedName("division")
    var division: String? = null

    @SerializedName("dateCreation")
    var dateCreation: String? = null

    @SerializedName("customerName")
    var customerName: String? = null

    private var dtCreation : Date? = null

    fun getDate(): Date? {
        if (dtCreation == null) {
            dtCreation = Utils.toDate(dateCreation ?: "", "dd/MM/yyyy")
        }
        return dtCreation
    }

    fun getValue(): Double {
        return Utils.roundToDecimals(valor ?: 0.00, 2)
    }

    constructor()

    constructor(dateCreation: String?) {
        this.dateCreation = dateCreation
    }

    constructor(valor: Double?) {
        this.valor = valor
    }

    constructor(
        numeroPedido: String?,
        quantidade: Int?,
        valor: Double?,
        division: String?,
        dateCreation: String?,
        customerName: String?
    ) {
        this.numeroPedido = numeroPedido
        this.quantidade = quantidade
        this.valor = valor
        this.division = division
        this.dateCreation = dateCreation
        this.customerName = customerName
    }

    constructor(source: Parcel) : this(
        source.readString(),
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Double::class.java.classLoader) as Double?,
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(numeroPedido)
        writeValue(quantidade)
        writeValue(valor)
        writeString(division)
        writeString(dateCreation)
        writeString(customerName)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PedidoClienteResponse> = object : Parcelable.Creator<PedidoClienteResponse> {
            override fun createFromParcel(source: Parcel): PedidoClienteResponse = PedidoClienteResponse(source)
            override fun newArray(size: Int): Array<PedidoClienteResponse?> = arrayOfNulls(size)
        }
    }
}