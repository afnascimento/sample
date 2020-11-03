package com.unilever.julia.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.unilever.julia.utils.Utils
import java.util.*

class NotificacaoModel : Parcelable {

    var header = ""

    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("date")
    @Expose
    var date: String? = null

    @SerializedName("read")
    @Expose
    var isRead: Boolean? = null

    @SerializedName("detail")
    @Expose
    var detail: Detail? = null

    private var dtCreation : Date? = null

    fun getDate(): Date? {
        if (dtCreation == null) {
            dtCreation = Utils.toDate(date ?: "", "dd/MM/yyyy")
        }
        return dtCreation
    }

    override fun toString(): String {
        return "[Header: " + header +
                ", Título: " + title +
                ", Description: " + description +
                ", Date: " + date +
                ", IsRead: " + isRead +
                ", Detail: " + detail + "]"
    }

    class Detail : Parcelable {

        @SerializedName("title")
        @Expose
        var title : String? = null

        @SerializedName("description")
        @Expose
        var description : String? = null

        @SerializedName("attached")
        @Expose
        var attached : MutableList<Attached>? = null

        constructor()

        constructor(
            title: String?,
            description: String?,
            attached: MutableList<Attached>?
        ) {
            this.title = title
            this.description = description
            this.attached = attached
        }

        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.createTypedArrayList(Attached.CREATOR)
        )

        override fun describeContents() = 0

        override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
            parcel.writeString(title)
            parcel.writeString(description)
            parcel.writeTypedList(attached)
        }

        companion object CREATOR : Parcelable.Creator<Detail> {
            override fun createFromParcel(parcel: Parcel): Detail {
                return Detail(parcel)
            }

            override fun newArray(size: Int): Array<Detail?> {
                return arrayOfNulls(size)
            }
        }

        override fun toString(): String {
            return "[Título: " + title + ", Descrição: " + description + ", Anexos: " + attached + "]"
        }
    }

    class Attached : Parcelable {

        @SerializedName("type")
        @Expose
        var type: String? = null

        @SerializedName("url")
        @Expose
        var url: String? = null

        @SerializedName("label")
        @Expose
        var label: String? = null

        @SerializedName("intencao")
        @Expose
        var intencao: String? = null

        constructor()

        constructor(type: String?, url: String?, label: String?, intencao: String?) {
            this.type = type
            this.url = url
            this.label = label
            this.intencao = intencao
        }

        constructor(parcel: Parcel) {
            type = parcel.readString()
            url = parcel.readString()
            label = parcel.readString()
            intencao = parcel.readString()
        }

        override fun describeContents() = 0

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(type)
            parcel.writeString(url)
            parcel.writeString(label)
            parcel.writeString(intencao)
        }

        companion object CREATOR : Parcelable.Creator<Attached> {
            override fun createFromParcel(parcel: Parcel): Attached {
                return Attached(parcel)
            }

            override fun newArray(size: Int): Array<Attached?> {
                return arrayOfNulls(size)
            }
        }

        override fun toString(): String {
            return "[Tipo: " + type + ", URL: " + url + ", Label: " + label + ", Intenção: " + intencao + "]"
        }
    }

    constructor()

    constructor(nHeader: String, nDate: String, nIsRead: Boolean) {
        header = nHeader
        date = nDate
        isRead = nIsRead
    }

    constructor(parcel: Parcel) {
        id = parcel.readString()
        title = parcel.readString()
        description = parcel.readString()
        date = parcel.readString()
        isRead = parcel.readInt() == 1
        detail = parcel.readValue(Detail.javaClass.classLoader) as Detail?
    }

    override fun describeContents() = 0

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(date)
        parcel.writeInt(if(isRead == true) 1 else 0)
        parcel.writeValue(detail)
    }

    companion object CREATOR : Parcelable.Creator<NotificacaoModel> {
        override fun createFromParcel(parcel: Parcel): NotificacaoModel {
            return NotificacaoModel(parcel)
        }

        override fun newArray(size: Int): Array<NotificacaoModel?> {
            return arrayOfNulls(size)
        }
    }
}