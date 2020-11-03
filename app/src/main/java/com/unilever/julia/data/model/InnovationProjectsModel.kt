package com.unilever.julia.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.*
import com.google.gson.annotations.SerializedName
import com.unilever.julia.utils.*
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

class InnovationProjectsModel {

    @SerializedName("projetos")
    var projects: MutableList<Project>? = arrayListOf()

    fun addProject(project : Project) : InnovationProjectsModel {
        projects?.add(project)
        return this
    }

    data class Project(
        @SerializedName("name")
        var name: String? = null,

        @SerializedName("itens")
        var items: MutableList<Item>? = arrayListOf()
    ) {
        fun setName(name: String): Project {
            this.name = name
            return this
        }

        fun addItem(item: Item): Project {
            this.items?.add(item)
            return this
        }

        fun getSortedItemsByDate(): MutableList<Item> {
            return items?.sortedBy { it.dataParse }?.toMutableList() ?: arrayListOf()
        }

        fun groupByMonthAndYearInDate(): Map<String, List<Item>> {
            return getSortedItemsByDate().groupBy { it.getMonthYearAsText()}
        }
    }

    data class Item(
        @SerializedName("title")
        var title: String? = null,
        @SerializedName("image")
        var image: String? = null,
        @SerializedName("data")
        var data: String? = null,
        @SerializedName("canal")
        var canal: String? = null,
        @SerializedName("codeProduto")
        var codeProduto: String? = null,
        @SerializedName("identifier")
        var identifier: String? = null,
        @Transient
        var dataParse: Date? = null,
        @Transient
        var isTitle: Boolean = false,
        @Transient
        var sessionCode: String = "",
        @Transient
        var nextIntent: String = "") : Parcelable {

        constructor(parcel: Parcel) : this() {
            title = parcel.readString()
            image = parcel.readString()
            data = parcel.readString()
            canal = parcel.readString()
            codeProduto = parcel.readString()
            identifier = parcel.readString()

            val time = parcel.readLong()
            dataParse = if (time == -1L) null else Date(time)

            isTitle = parcel.readByte() != 0.toByte()

            sessionCode = parcel.readString() ?: ""
            nextIntent = parcel.readString() ?: ""
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(title)
            parcel.writeString(image)
            parcel.writeString(data)
            parcel.writeString(canal)
            parcel.writeString(codeProduto)
            parcel.writeString(identifier)

            val time: Long = dataParse?.time?.let { it } ?: -1
            parcel.writeLong(time)

            parcel.writeByte(if (isTitle) 1 else 0)

            parcel.writeString(sessionCode)
            parcel.writeString(nextIntent)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Item> {
            override fun createFromParcel(parcel: Parcel): Item {
                return Item(parcel)
            }

            override fun newArray(size: Int): Array<Item?> {
                return arrayOfNulls(size)
            }
        }

        fun getDataText(): String {
            val date : Date = dataParse ?: return ""
            return date.parseString(ParsePatternsEnums.DDMMYYYY)
        }

        fun getMonthYearAsText(): String {
            val date : Date = dataParse ?: return ""

            //val month = date.monthOfYearAsText()
            //val year = date.year()

            val cal = Calendar.getInstance()
            cal.time = date
            val month = SimpleDateFormat("MMMM").format(date)
            val year = cal.get(Calendar.YEAR)

            return "$month, $year".capitalizeAllText()
        }

        fun setTitle(title: String): Item {
            this.title = title
            return this
        }

        fun setData(data: String): Item {
            this.data = data
            this.dataParse = data.parseDate(ParsePatternsEnums.DDMMYYYY)
            return this
        }
    }

    class Deserializer(private val mSessionCode: String, private val mNextIntent : String) : JsonDeserializer<Item> {

        private val mGson = Gson()

        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Item {
            try {
                val jsonObject : JsonObject = json?.asJsonObject ?: return Item()

                val response = mGson.fromJson(jsonObject, Item::class.java)

                response.apply {
                    this.dataParse = data?.parseDate(
                        ParsePatternsEnums.DDMMYYYY,
                        ParsePatternsEnums.YYYYMMDD_HHMMSS_SSS
                    )
                    this.sessionCode = mSessionCode
                    this.nextIntent = mNextIntent
                }

                return response
            } catch (e : Throwable) {
                throw e
            }
        }
    }
}