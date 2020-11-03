package com.unilever.julia.data.model.notificacao

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.*
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName

data class Detail(
    @SerializedName("title")
    var title: String? = "",
    @SerializedName("description")
    var description: String? = "",
    @JsonAdapter(Deserializer::class)
    @SerializedName("attached")
    var attached: List<Attached>? = listOf()
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(Attached)
    )

    class Deserializer : JsonDeserializer<List<Attached>> {

        private val mGson = Gson()

        override fun deserialize(json: JsonElement?, typeOfT: java.lang.reflect.Type?, context: JsonDeserializationContext?): List<Attached> {
            val jsonArray : JsonArray = json?.asJsonArray ?: return listOf()
            val items = mGson.fromJson(jsonArray, Array<Attached>::class.java).toMutableList()
            return items.filterNot { it.type == null }.toMutableList()
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeTypedList(attached)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Detail> {
        override fun createFromParcel(parcel: Parcel): Detail {
            return Detail(parcel)
        }

        override fun newArray(size: Int): Array<Detail?> {
            return arrayOfNulls(size)
        }
    }
}