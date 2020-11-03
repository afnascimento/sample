package com.unilever.julia.data.model

import android.os.Parcel
import android.os.Parcelable
import com.unilever.julia.data.enums.StatusCardEnum
import com.unilever.julia.utils.NumberUtils
import org.apache.commons.lang3.StringUtils

class StatusPedidoItem : Parcelable, IStatusPedido {

    var content: Content = Content()
    var values: MutableList<KeySet> = arrayListOf()
    var transports: MutableList<Transport> = arrayListOf()

    var statusCardEnum : StatusCardEnum = StatusCardEnum.CARD

    override fun getType(): StatusCardEnum {
        return statusCardEnum
    }

    fun addValue(field: Field, response: StatusPedidoResponse, addIfEmpty: Boolean) {
        var value = ""

        if (field == Field.PRODUTO) {
            value = response.materialName ?: ""
            content.produto = value
        } else if (field == Field.COD_SKU) {
            value = response.codProduto ?: ""
            content.codSKU = value
        } else if (field == Field.DATA_CRIACAO) {
            value = response.dataCriacao ?: ""
            content.dataCriacao = value
        } else if (field == Field.DATA_REQUERIDA) {
            value = response.dataRequerida ?: ""
            content.dataRequerida = value
        } else if (field == Field.VALOR) {
            val valDouble = NumberUtils.toDouble(response.valorProduto)
            value = NumberUtils.toCurrencyBrazil(valDouble, true)
            content.valor = value
        } else if (field == Field.QUANTIDADE) {
            value = response.quantidade ?: ""
            content.quantidade = value
        } else if (field == Field.DATA_AGENDA) {
            value = response.dataAgenda ?: ""
            content.dataAgenda = value
        } else if (field == Field.MOTIVO) {
            value = response.motivo ?: ""
            content.motivo = value
        }

        if (addIfEmpty) {
            values.add(KeySet(field.text, value))
        } else {
            // only if not empty
            if (StringUtils.isNotEmpty(value)) {
                values.add(KeySet(field.text, value))
            }
        }
    }

    fun addTransport(code: String, date: String, quantity: String) {
        transports.add(Transport(code, date, quantity))
    }

    enum class Type(var value: String) {
        PDTM("PDTM"),
        PDIS("PDIS"),
        OTHERS("OTHERS");
    }

    enum class Field(var text: String) {
        PRODUTO("Produto"),
        COD_SKU("Cod SKU"),
        DATA_CRIACAO("Dt Criação"),
        DATA_REQUERIDA("Dt. Req. Cliente"),
        VALOR("Valor"),
        QUANTIDADE("Caixas"),
        DATA_AGENDA("Dt. Agenda"),
        MOTIVO("Motivo");
    }

    class KeySet(var key: String, var value: String) : Parcelable {
        constructor(source: Parcel) : this(
            source.readString() ?: "",
            source.readString() ?: ""
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeString(key)
            writeString(value)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<KeySet> = object : Parcelable.Creator<KeySet> {
                override fun createFromParcel(source: Parcel): KeySet = KeySet(source)
                override fun newArray(size: Int): Array<KeySet?> = arrayOfNulls(size)
            }
        }
    }

    class Transport(var code: String, var date: String, var quantity: String) : Parcelable {
        constructor(source: Parcel) : this(
            source.readString() ?: "",
            source.readString() ?: "",
            source.readString() ?: ""
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeString(code)
            writeString(date)
            writeString(quantity)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<Transport> = object : Parcelable.Creator<Transport> {
                override fun createFromParcel(source: Parcel): Transport = Transport(source)
                override fun newArray(size: Int): Array<Transport?> = arrayOfNulls(size)
            }
        }
    }

    class Content : Parcelable {

        var produto = ""
        var codSKU = ""
        var dataCriacao = ""
        var dataRequerida = ""
        var valor = ""
        var quantidade = ""
        var dataAgenda = ""
        var motivo = ""

        constructor()

        constructor(source: Parcel) : this(
            source.readString() ?: "",
            source.readString() ?: "",
            source.readString() ?: "",
            source.readString() ?: "",
            source.readString() ?: "",
            source.readString() ?: "",
            source.readString() ?: "",
            source.readString() ?: ""
        )

        constructor(
            produto: String,
            codSKU: String,
            dataCriacao: String,
            dataRequerida: String,
            valor: String,
            quantidade: String,
            dataAgenda: String,
            motivo: String
        ) {
            this.produto = produto
            this.codSKU = codSKU
            this.dataCriacao = dataCriacao
            this.dataRequerida = dataRequerida
            this.valor = valor
            this.quantidade = quantidade
            this.dataAgenda = dataAgenda
            this.motivo = motivo
        }

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeString(produto)
            writeString(codSKU)
            writeString(dataCriacao)
            writeString(dataRequerida)
            writeString(valor)
            writeString(quantidade)
            writeString(dataAgenda)
            writeString(motivo)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<Content> = object : Parcelable.Creator<Content> {
                override fun createFromParcel(source: Parcel): Content = Content(source)
                override fun newArray(size: Int): Array<Content?> = arrayOfNulls(size)
            }
        }
    }

    constructor()

    constructor(source: Parcel) : this(
        source.readParcelable<Content>(Content::class.java.classLoader) ?: Content(),
        source.createTypedArrayList(KeySet.CREATOR) ?: arrayListOf(),
        source.createTypedArrayList(Transport.CREATOR) ?: arrayListOf()
    )

    constructor(content: Content, values: MutableList<KeySet>, transports: MutableList<Transport>) {
        this.content = content
        this.values = values
        this.transports = transports
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelable(content, 0)
        writeTypedList(values)
        writeTypedList(transports)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<StatusPedidoItem> = object : Parcelable.Creator<StatusPedidoItem> {
            override fun createFromParcel(source: Parcel): StatusPedidoItem = StatusPedidoItem(source)
            override fun newArray(size: Int): Array<StatusPedidoItem?> = arrayOfNulls(size)
        }

        fun isType(value: String, type: Type): Boolean {
            return StringUtils.equalsIgnoreCase(value, type.value)
        }
    }
}