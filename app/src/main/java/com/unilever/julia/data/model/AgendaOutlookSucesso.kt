package com.unilever.julia.data.model

import android.os.Parcel
import android.os.Parcelable
import com.unilever.julia.components.enums.ComponentsType
import com.unilever.julia.components.model.IComponentsModel

class AgendaOutlookSucesso : Parcelable, IComponentsModel {

    var text : String? = null
    var context : AgendaOutlookContext? = null

    override fun getType(): ComponentsType {
        return ComponentsType.CARD_AGENDA_EDICAO
    }

    constructor(text: String?, context: AgendaOutlookContext?) {
        this.text = text
        this.context = context
    }

    constructor(source: Parcel) : this(
        source.readString(),
        source.readParcelable<AgendaOutlookContext>(AgendaOutlookContext::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(text)
        writeParcelable(context, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<AgendaOutlookSucesso> = object : Parcelable.Creator<AgendaOutlookSucesso> {
            override fun createFromParcel(source: Parcel): AgendaOutlookSucesso = AgendaOutlookSucesso(source)
            override fun newArray(size: Int): Array<AgendaOutlookSucesso?> = arrayOfNulls(size)
        }
    }
}