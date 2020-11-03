package com.unilever.julia.components.boletim

import android.os.Parcel
import android.os.Parcelable
import com.unilever.julia.components.TagsModel

data class BoletimFiltros(
    var attendance: List<TagsModel> = emptyList(),
    var brand: List<TagsModel> = emptyList(),
    var category: List<TagsModel> = emptyList(),
    var commodity: List<TagsModel> = emptyList(),
    var customer: List<TagsModel> = emptyList(),
    var channel: List<TagsModel> = emptyList()
) : Parcelable {

    fun isEmpty(): Boolean {
        return (attendance.isEmpty()
                && brand.isEmpty()
                && category.isEmpty()
                && commodity.isEmpty()
                && customer.isEmpty()
                && channel.isEmpty())
    }

    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(TagsModel) ?: emptyList(),
        parcel.createTypedArrayList(TagsModel) ?: emptyList(),
        parcel.createTypedArrayList(TagsModel) ?: emptyList(),
        parcel.createTypedArrayList(TagsModel) ?: emptyList(),
        parcel.createTypedArrayList(TagsModel) ?: emptyList(),
        parcel.createTypedArrayList(TagsModel) ?: emptyList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(attendance)
        parcel.writeTypedList(brand)
        parcel.writeTypedList(category)
        parcel.writeTypedList(commodity)
        parcel.writeTypedList(customer)
        parcel.writeTypedList(channel)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BoletimFiltros> {
        override fun createFromParcel(parcel: Parcel): BoletimFiltros {
            return BoletimFiltros(parcel)
        }

        override fun newArray(size: Int): Array<BoletimFiltros?> {
            return arrayOfNulls(size)
        }
    }
}