package com.unilever.julia.data.model.inovacaoDetalhe

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.unilever.julia.utils.ParsePatternsEnums
import com.unilever.julia.utils.parseDate
import com.unilever.julia.utils.parseString

data class Resumo(
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("data")
    var `data`: String? = "",
    @SerializedName("bgColor")
    var bgColor: String? = "",
    @SerializedName("canal")
    var canal: String? = "",
    @SerializedName("iconCanal")
    var iconCanal: String? = "",
    @SerializedName("iconColor")
    var iconColor: String? = "",
    @SerializedName("iconData")
    var iconData: String? = "",
    @SerializedName("tradeStory")
    var tradeStory: TradeStory? = null,
    @SerializedName("midias")
    var midias: Midias? = null,
    @SerializedName("beneficioEsperado")
    var beneficioEsperado: BeneficioEsperado? = null,
    @SerializedName("publicoAlvo")
    var publicoAlvo: TitleAndDescription? = null
) {
    data class BeneficioEsperado(
        @SerializedName("title")
        var title: String? = "",
        @SerializedName("descCustomer")
        var descriptionCustomer: String? = "",
        @SerializedName("descShopkeeper")
        var descriptionShopKeeper: String? = ""
    )

    data class TradeStory(
        @SerializedName("avaliacao")
        var avaliacao: Int? = null,
        @SerializedName("count")
        var count: Int? = null,
        @SerializedName("iconTradestory")
        var iconTradestory: String? = "",
        @SerializedName("title")
        var title: String? = "",
        @SerializedName("url")
        var url: String? = "",
        @SerializedName("isDocStoreChanged")
        var novo : Boolean? = false
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeValue(avaliacao)
            parcel.writeValue(count)
            parcel.writeString(iconTradestory)
            parcel.writeString(title)
            parcel.writeString(url)
            parcel.writeValue(novo)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<TradeStory> {
            override fun createFromParcel(parcel: Parcel): TradeStory {
                return TradeStory(parcel)
            }

            override fun newArray(size: Int): Array<TradeStory?> {
                return arrayOfNulls(size)
            }
        }
    }

    data class Midias(
        @SerializedName("title")
        var title: String? = "",
        @SerializedName("itens")
        var itens: MutableList<Item>? = null
    )

    data class Item(
        @SerializedName("description")
        var description: String? = "",
        @SerializedName("title")
        var title: String? = "",
        @SerializedName("url")
        var url: String? = ""
    )

    /*
    data class PublicoAlvo(
        @SerializedName("title")
        var title: String? = "",
        @SerializedName("description")
        var description: String? = ""
    )
    */

    fun getDataGeral(): String {
        val date = data?.parseDate(ParsePatternsEnums.DDMMYYYY, ParsePatternsEnums.YYYYMMDD_HHMMSS_SSS)
        if (date != null) {
            return date.parseString(ParsePatternsEnums.DDMMYYYY)
        }
        return data ?: ""
    }
}