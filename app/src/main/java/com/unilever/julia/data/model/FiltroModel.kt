package com.unilever.julia.data.model

import android.os.Parcel
import android.os.Parcelable
import com.unilever.julia.data.enums.FiltroOrdenacaoEnum
import java.util.*

class FiltroModel : Parcelable {

    var periodo: Pair<Date, Date>? = null
    var valores: Pair<Double, Double>? = null
    var ordenacao : FiltroOrdenacaoEnum? = null

    fun isClearFilter(): Boolean {
        return periodo == null && valores == null && ordenacao == null
    }

    fun clear() {
        periodo = null
        valores = null
        ordenacao = null
    }

    constructor()

    constructor(source: Parcel) : this(
        source.readSerializable() as Pair<Date, Date>?,
        source.readSerializable() as Pair<Double, Double>?,
        source.readValue(Int::class.java.classLoader)?.let { FiltroOrdenacaoEnum.values()[it as Int] }
    )

    constructor(periodo: Pair<Date, Date>?, valores: Pair<Double, Double>?, ordenacao: FiltroOrdenacaoEnum?) {
        this.periodo = periodo
        this.valores = valores
        this.ordenacao = ordenacao
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeSerializable(periodo)
        writeSerializable(valores)
        writeValue(ordenacao?.ordinal)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<FiltroModel> = object : Parcelable.Creator<FiltroModel> {
            override fun createFromParcel(source: Parcel): FiltroModel = FiltroModel(source)
            override fun newArray(size: Int): Array<FiltroModel?> = arrayOfNulls(size)
        }
    }
}