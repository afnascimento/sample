package com.unilever.julia.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class AgendaOutlookContext : Parcelable {

    @SerializedName("id")
    var id: String? = null

    @SerializedName("assunto")
    var assunto: String? = null

    @SerializedName("dataInicio")
    var dataInicio: String? = null

    @SerializedName("horaInicio")
    var horaInicio: String? = null

    @SerializedName("dataFim")
    var dataFim: String? = null

    @SerializedName("horaFim")
    var horaFim: String? = null

    @SerializedName("timeZone")
    var timeZone: String? = null

    @SerializedName("local")
    var local: String? = null

    var owner : Boolean = false

    @SerializedName("participantes")
    var participantes: MutableList<AgendaOutlookParticipante>? = null

    constructor()

    constructor(
        id: String?,
        assunto: String?,
        dataInicio: String?,
        horaInicio: String?,
        dataFim: String?,
        horaFim: String?,
        timeZone: String?,
        local: String?,
        owner : Boolean,
        participantes: MutableList<AgendaOutlookParticipante>?
    ) {
        this.id = id
        this.assunto = assunto
        this.dataInicio = dataInicio
        this.horaInicio = horaInicio
        this.dataFim = dataFim
        this.horaFim = horaFim
        this.timeZone = timeZone
        this.local = local
        this.owner = owner
        this.participantes = participantes
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
        1 == source.readInt(),
        source.createTypedArrayList(AgendaOutlookParticipante.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(assunto)
        writeString(dataInicio)
        writeString(horaInicio)
        writeString(dataFim)
        writeString(horaFim)
        writeString(timeZone)
        writeString(local)
        writeInt((if (owner) 1 else 0))
        writeTypedList(participantes)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<AgendaOutlookContext> = object : Parcelable.Creator<AgendaOutlookContext> {
            override fun createFromParcel(source: Parcel): AgendaOutlookContext = AgendaOutlookContext(source)
            override fun newArray(size: Int): Array<AgendaOutlookContext?> = arrayOfNulls(size)
        }
    }
}