package com.unilever.julia.data.model.notificacao

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.*
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.unilever.julia.data.model.notificacao.holder.AttachedViewType
import com.unilever.julia.utils.getString

@JsonAdapter(Attached.Deserializer::class)
data class Attached(
    @SerializedName("type")
    var type: Type? = null,
    @SerializedName("label")
    var label: String? = "",
    @SerializedName("url")
    var url: String? = ""
) : Parcelable, AttachedViewType {

    override fun getViewType(): Type {
        return type ?: Type.NOT_FOUND
    }

    constructor(parcel: Parcel) : this(
        Type.getByName(parcel.readString() ?: ""),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    class Deserializer : JsonDeserializer<Attached> {

        override fun deserialize(json: JsonElement?, typeOfT: java.lang.reflect.Type?, context: JsonDeserializationContext?): Attached {
            val jsonObject : JsonObject = json?.asJsonObject ?: return Attached()

            val attached = Attached()

            val type : String = jsonObject.getString("type") ?: ""
            attached.type = Type.getByName(type)
            attached.url = jsonObject.getString("url")
            attached.label = jsonObject.getString("label")

            return attached
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type?.name ?: "")
        parcel.writeString(label)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Attached> {
        override fun createFromParcel(parcel: Parcel): Attached {
            return Attached(parcel)
        }

        override fun newArray(size: Int): Array<Attached?> {
            return arrayOfNulls(size)
        }
    }
}