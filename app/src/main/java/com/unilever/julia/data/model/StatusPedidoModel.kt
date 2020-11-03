package com.unilever.julia.data.model

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import com.unilever.julia.components.enums.ComponentsType
import com.unilever.julia.utils.AppConstants
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.unilever.julia.components.model.IComponentsModel
import com.unilever.julia.data.enums.StatusCardEnum
import com.unilever.julia.data.enums.StatusEnum
import com.unilever.julia.logger.Logger
import com.unilever.julia.utils.NumberUtils
import org.apache.commons.lang3.StringUtils
import java.lang.Exception

class StatusPedidoModel : IComponentsModel {

    override fun getType(): ComponentsType {
        return ComponentsType.STATUS_PEDIDO
    }

    var text : String = ""

    var items : MutableList<Item> = arrayListOf()

    class Item : Parcelable, IStatusPedido {

        override fun getType(): StatusCardEnum {
            return StatusCardEnum.CARD
        }

        @SerializedName("order")
        @Expose
        var order: Order? = null

        @SerializedName("code")
        @Expose
        var code: String? = null

        constructor()

        constructor(order: Order?, code: String?) {
            this.order = order
            this.code = code
        }

        constructor(source: Parcel) : this(
            source.readParcelable<Order>(Order::class.java.classLoader),
            source.readString()
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeParcelable(order, 0)
            writeString(code)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<Item> = object : Parcelable.Creator<Item> {
                override fun createFromParcel(source: Parcel): Item = Item(source)
                override fun newArray(size: Int): Array<Item?> = arrayOfNulls(size)
            }
        }

        fun getStatusEnum(): StatusEnum {
            return StatusEnum.getStatusEnum(order?.statusPedido ?: "")
        }

        fun getStatusPedidoText(context: Context): String {
            val status = getStatusEnum()

            if (status == StatusEnum.FATURADO || status == StatusEnum.ANULADO) {
                return status.getText(context)
            } else {
                return StringUtils.trimToEmpty(order?.statusPedido)
            }
        }

        private fun getIconsArray(): Array<out String> {
            try {
                return StringUtils.split(order?.icon, ";")
            } catch (e : Exception) {
                return emptyArray()
            }
        }

        fun getIconMoney(): String {
            try {
                return getIconsArray()[0]
            } catch (e : Exception) {
                Logger.error(AppConstants.TAG_LOG, "", e)
            }
            return ""
        }

        fun getIconCaixa(): String {
            try {
                return getIconsArray()[1]
            } catch (e : Exception) {
                Logger.error(AppConstants.TAG_LOG, "", e)
            }
            return ""
        }

        fun getIconStatus(): String {
            try {
                return getIconsArray()[2]
            } catch (e : Exception) {
                Logger.error(AppConstants.TAG_LOG, "", e)
            }
            return ""
        }
    }

    class Order : Parcelable {

        @SerializedName("idCustomer")
        @Expose
        var idCustomer: String? = null

        @SerializedName("descCustomer")
        @Expose
        var descCustomer: String? = null

        @SerializedName("valor")
        @Expose
        var valor: String? = null

        @SerializedName("type")
        @Expose
        var type: String? = null

        @SerializedName("amount")
        @Expose
        var amount: String? = null

        @SerializedName("icon")
        @Expose
        var icon: String? = null

        @SerializedName("color")
        @Expose
        var color: String? = null

        @SerializedName("codPedido")
        @Expose
        var codPedido: String? = null

        @SerializedName("statusPedido")
        @Expose
        var statusPedido: String? = null

        @SerializedName("idCrm")
        @Expose
        var idCrm: String? = null

        @SerializedName("idCustomerOrder")
        @Expose
        var idCustomerOrder: String? = null

        fun getValorPedido(): String {
            return NumberUtils.toCurrencyBrazil(NumberUtils.toDouble(valor), true)
        }

        constructor(idCustomer: String?, descCustomer: String?, valor: String?,
                    type: String?, amount: String?, icon: String?,
                    color: String?, codPedido: String?, statusPedido: String?,
                    idCrm: String?, idCustomerOrder: String?) {
            this.idCustomer = idCustomer
            this.descCustomer = descCustomer
            this.valor = valor
            this.type = type
            this.amount = amount
            this.icon = icon
            this.color = color
            this.codPedido = codPedido
            this.statusPedido = statusPedido
            this.idCrm = idCrm
            this.idCustomerOrder = idCustomerOrder
        }

        constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeString(idCustomer)
            writeString(descCustomer)
            writeString(valor)
            writeString(type)
            writeString(amount)
            writeString(icon)
            writeString(color)
            writeString(codPedido)
            writeString(statusPedido)
            writeString(idCrm)
            writeString(idCustomerOrder)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<Order> = object : Parcelable.Creator<Order> {
                override fun createFromParcel(source: Parcel): Order = Order(source)
                override fun newArray(size: Int): Array<Order?> = arrayOfNulls(size)
            }
        }
    }
}