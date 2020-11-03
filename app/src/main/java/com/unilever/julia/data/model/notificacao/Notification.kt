package com.unilever.julia.data.model.notificacao

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.*
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.unilever.julia.data.model.notificacao.holder.NotificationViewType
import com.unilever.julia.utils.*
import java.util.*

@JsonAdapter(Notification.Deserializer::class)
data class Notification(
    @SerializedName("id")
    var id: Int? = 0,
    @SerializedName("title")
    var title: String? = "",
    @SerializedName("description")
    var description: String? = "",
    @SerializedName("date")
    var date: Date? = null,
    @SerializedName("read")
    var read: Boolean? = false,
    @SerializedName("detail")
    var detail: Detail? = Detail()
) : NotificationViewType, Parcelable {

    fun getTitleDetail(): String {
        var text : String = title ?: ""
        if (detail != null) {
            text = detail?.title ?: ""
        }
        return text
    }

    fun getDescriptionDetail(): String {
        var text : String = description ?: ""
        if (detail != null) {
            text = detail?.description ?: ""
        }
        return text
    }

    fun getIdString(): String {
        val mID : Int = id ?: 0
        if (mID > 0)
            return mID.toString()
        return ""
    }

    fun isRead(): Boolean {
        return read ?: false
    }

    fun getDateString(): String {
        return date?.parseString(ParsePatternsEnums.DDMMYYYY) ?: ""
    }

    override fun getType(): NotificationViewType.Type {
        return NotificationViewType.Type.CARD
    }

    class Deserializer : JsonDeserializer<Notification> {

        private val mGson = Gson()

        override fun deserialize(json: JsonElement?, typeOfT: java.lang.reflect.Type?, context: JsonDeserializationContext?): Notification {
            val jsonObject : JsonObject = json?.asJsonObject ?: return Notification()

            val notification = Notification()
            notification.id = jsonObject.getInt("id")
            notification.title = jsonObject.getString("title")
            notification.description = jsonObject.getString("description")
            notification.date = jsonObject.getString("date")?.parseDate(ParsePatternsEnums.DDMMYYYY)
            notification.read = jsonObject.getBoolean("read")
            notification.detail = mGson.fromJson(jsonObject.get("detail"), Detail::class.java)

            return notification
        }
    }

    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString()?.parseDate(ParsePatternsEnums.DDMMYYYY),
        parcel.readInt() == 1,
        parcel.readParcelable(Detail::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(getDateString())
        parcel.writeInt(if(read == true) 1 else 0)
        parcel.writeParcelable(detail, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Notification> {
        override fun createFromParcel(parcel: Parcel): Notification {
            return Notification(parcel)
        }

        override fun newArray(size: Int): Array<Notification?> {
            return arrayOfNulls(size)
        }
    }
}