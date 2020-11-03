package com.unilever.julia.data.model

import android.os.Parcel
import android.os.Parcelable
import com.unilever.julia.components.enums.ComponentsType
import com.unilever.julia.components.model.IComponentsModel

class PedidosClienteModel : IComponentsModel, Parcelable {

    var text: String = ""
    var code: String = ""
    var customerId: String = ""
    var sessionCode: String = ""

    override fun getType(): ComponentsType {
        return ComponentsType.PEDIDO_CLIENTE_MESSAGE
    }

    constructor()

    constructor(source: Parcel) : this(
        source.readString() ?: "",
        source.readString() ?: "",
        source.readString() ?: "",
        source.readString() ?: ""
    )

    constructor(text: String, code: String, customerId: String, sessionCode: String) {
        this.text = text
        this.code = code
        this.customerId = customerId
        this.sessionCode = sessionCode
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(text)
        writeString(code)
        writeString(customerId)
        writeString(sessionCode)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PedidosClienteModel> = object : Parcelable.Creator<PedidosClienteModel> {
            override fun createFromParcel(source: Parcel): PedidosClienteModel = PedidosClienteModel(source)
            override fun newArray(size: Int): Array<PedidosClienteModel?> = arrayOfNulls(size)
        }
    }
}