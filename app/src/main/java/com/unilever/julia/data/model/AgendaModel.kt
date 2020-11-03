package com.unilever.julia.data.model

import android.os.Parcel
import android.os.Parcelable
import com.unilever.julia.components.enums.ComponentsType
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.unilever.julia.components.model.IComponentsModel
import com.unilever.julia.utils.Utils
import org.apache.commons.lang3.StringUtils
import java.util.*

class AgendaModel : IComponentsModel {

    override fun getType(): ComponentsType {
        return ComponentsType.AGENDA
    }

    var text : String = ""

    var items : MutableList<Item> = arrayListOf()

    class Item : Parcelable {

        @SerializedName("Id")
        @Expose
        var id : String? = null

        @SerializedName("Subject")
        @Expose
        var subject : String? = null

        @SerializedName("Description")
        @Expose
        var description : String? = null

        @SerializedName("ActivityDate")
        @Expose
        var activityDate : String? = null

        @SerializedName("Owner")
        @Expose
        var owner : Boolean = false

        @SerializedName("OutlookCalendar")
        @Expose
        var outlookCalendar : OutlookCalendar? = null

        var dataDate : Date? = null

        var dataVisible : Boolean = false

        fun isEventOutlook(): Boolean {
            return StringUtils.containsIgnoreCase(subject ?: "", "outlook")
        }

        fun getData() : Date? {
            return Utils.toDate(activityDate ?: "", "dd/MM/yyyy")
        }

        constructor()

        constructor(
            id: String?,
            subject: String?,
            description: String?,
            activityDate: String?,
            owner: Boolean,
            outlookCalendar: OutlookCalendar?
        ) {
            this.id = id
            this.subject = subject
            this.description = description
            this.activityDate = activityDate
            this.owner = owner
            this.outlookCalendar = outlookCalendar
        }

        constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            1 == source.readInt(),
            source.readParcelable<OutlookCalendar>(OutlookCalendar::class.java.classLoader)
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeString(id)
            writeString(subject)
            writeString(description)
            writeString(activityDate)
            writeInt((if (owner) 1 else 0))
            writeParcelable(outlookCalendar, 0)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<Item> = object : Parcelable.Creator<Item> {
                override fun createFromParcel(source: Parcel): Item = Item(source)
                override fun newArray(size: Int): Array<Item?> = arrayOfNulls(size)
            }
        }
    }

    class OutlookCalendar : Parcelable {

        @SerializedName("Id")
        var id: String? = null

        @SerializedName("Assunto")
        var assunto: String? = null

        @SerializedName("DataInicio")
        var dataInicio: String? = null

        @SerializedName("HoraInicio")
        var horaInicio: String? = null

        @SerializedName("DataFim")
        var dataFim: String? = null

        @SerializedName("HoraFim")
        var horaFim: String? = null

        @SerializedName("Local")
        var local: String? = null

        @SerializedName("Participantes")
        var participantes: MutableList<OutlookCalendarParticipante>? = null

        constructor()

        constructor(
            id: String?,
            assunto: String?,
            dataInicio: String?,
            horaInicio: String?,
            dataFim: String?,
            horaFim: String?,
            local: String?,
            participantes: MutableList<OutlookCalendarParticipante>?
        ) {
            this.id = id
            this.assunto = assunto
            this.dataInicio = dataInicio
            this.horaInicio = horaInicio
            this.dataFim = dataFim
            this.horaFim = horaFim
            this.local = local
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
            source.createTypedArrayList(OutlookCalendarParticipante.CREATOR)
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeString(id)
            writeString(assunto)
            writeString(dataInicio)
            writeString(horaInicio)
            writeString(dataFim)
            writeString(horaFim)
            writeString(local)
            writeTypedList(participantes)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<OutlookCalendar> = object : Parcelable.Creator<OutlookCalendar> {
                override fun createFromParcel(source: Parcel): OutlookCalendar = OutlookCalendar(source)
                override fun newArray(size: Int): Array<OutlookCalendar?> = arrayOfNulls(size)
            }
        }
    }

    class OutlookCalendarParticipante : Parcelable {

        @SerializedName("Email")
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
            val CREATOR: Parcelable.Creator<OutlookCalendarParticipante> =
                object : Parcelable.Creator<OutlookCalendarParticipante> {
                    override fun createFromParcel(source: Parcel): OutlookCalendarParticipante =
                        OutlookCalendarParticipante(source)

                    override fun newArray(size: Int): Array<OutlookCalendarParticipante?> = arrayOfNulls(size)
                }
        }
    }
}