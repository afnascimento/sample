package com.unilever.julia.data.model

import android.os.Parcel
import android.os.Parcelable
import com.unilever.julia.components.enums.ComponentsType
import com.unilever.julia.components.model.IComponentsModel

class ReuniaoOutlookModel : IComponentsModel, Parcelable {

    var text: String = ""
    var sessionCode: String = ""

    override fun getType(): ComponentsType {
        return ComponentsType.REUNIAO_OUTLOOK
    }

    constructor()

    constructor(source: Parcel) : this(
        source.readString() ?: "",
        source.readString() ?: ""
    )

    constructor(text: String, sessionCode: String) {
        this.text = text
        this.sessionCode = sessionCode
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(text)
        writeString(sessionCode)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ReuniaoOutlookModel> = object : Parcelable.Creator<ReuniaoOutlookModel> {
            override fun createFromParcel(source: Parcel): ReuniaoOutlookModel = ReuniaoOutlookModel(source)
            override fun newArray(size: Int): Array<ReuniaoOutlookModel?> = arrayOfNulls(size)
        }
    }
}