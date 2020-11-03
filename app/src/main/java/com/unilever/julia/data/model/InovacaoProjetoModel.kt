package com.unilever.julia.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class InovacaoProjetoModel : Parcelable {

    @SerializedName("Projeto_Inovacao")
    @Expose
    var projetoInovacao: String? = null

    @SerializedName("Canal")
    @Expose
    var canal: String? = null

    @SerializedName("Data_lancamento")
    @Expose var dataLancamento: String? = null

    @SerializedName("Beneficio_Esperado")
    @Expose
    var beneficioEsperado: String? = null

    @SerializedName("Link_TradeStory")
    @Expose
    var linkTradeStory: String? = null

    @SerializedName("Link_Material")
    @Expose
    var linkMaterial: String? = null

    @SerializedName("Link_Ficha")
    @Expose
    var linkFicha: String? = null

    @SerializedName("Regras_de_Ouro")
    @Expose
    var regrasDeOuro: String? = null

    @SerializedName("Publico_Alvo")
    @Expose
    var publicoAlvo: String? = null

    @SerializedName("Novos_SKUs")
    @Expose
    var novosSkus: String? = null

    @SerializedName("SKUs_delistados")
    @Expose
    var skusDeListados: String? = null

    @SerializedName("Categoria")
    @Expose
    var categoria: String? = null

    @SerializedName("Commodity")
    @Expose
    var commodity: String? = null

    @SerializedName("Marca")
    @Expose
    var marca: String? = null

    @SerializedName("Quatidade_SKUs")
    @Expose
    var quatidadeSkus: String?  = null

    constructor()

    constructor(parcel: Parcel) {
        projetoInovacao = parcel.readString()
        canal = parcel.readString()
        dataLancamento = parcel.readString()
        beneficioEsperado = parcel.readString()
        linkTradeStory = parcel.readString()
        linkMaterial = parcel.readString()
        linkFicha = parcel.readString()
        regrasDeOuro = parcel.readString()
        publicoAlvo = parcel.readString()
        novosSkus = parcel.readString()
        skusDeListados = parcel.readString()
        categoria = parcel.readString()
        commodity = parcel.readString()
        marca = parcel.readString()
        quatidadeSkus = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(projetoInovacao)
        parcel.writeString(canal)
        parcel.writeString(dataLancamento)
        parcel.writeString(beneficioEsperado)
        parcel.writeString(linkTradeStory)
        parcel.writeString(linkMaterial)
        parcel.writeString(linkFicha)
        parcel.writeString(regrasDeOuro)
        parcel.writeString(publicoAlvo)
        parcel.writeString(novosSkus)
        parcel.writeString(skusDeListados)
        parcel.writeString(categoria)
        parcel.writeString(commodity)
        parcel.writeString(marca)
        parcel.writeString(quatidadeSkus)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<InovacaoProjetoModel> {
        override fun createFromParcel(parcel: Parcel): InovacaoProjetoModel {
            return InovacaoProjetoModel(parcel)
        }

        override fun newArray(size: Int): Array<InovacaoProjetoModel?> {
            return arrayOfNulls(size)
        }
    }
}