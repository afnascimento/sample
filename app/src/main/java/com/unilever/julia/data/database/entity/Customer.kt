package com.unilever.julia.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.unilever.julia.data.database.EntitiesMetadata
import com.unilever.julia.ui.component.IAutoCompleteModel
import org.apache.commons.lang3.StringUtils
import java.lang.StringBuilder

@Entity(tableName = EntitiesMetadata.Customer.tableName)
class Customer : IAutoCompleteModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = EntitiesMetadata.Customer.id)
    var id : Int? = null

    @ColumnInfo(name = EntitiesMetadata.Customer.code)
    var code : String? = null

    @ColumnInfo(name = EntitiesMetadata.Customer.territory)
    var territory : String? = null

    @ColumnInfo(name = EntitiesMetadata.Customer.name)
    var name : String? = null

    @ColumnInfo(name = EntitiesMetadata.Customer.state)
    var state : String? = null

    @ColumnInfo(name = EntitiesMetadata.Customer.city)
    var city : String? = null

    @ColumnInfo(name = EntitiesMetadata.Customer.district)
    var district : String? = null

    @ColumnInfo(name = EntitiesMetadata.Customer.textToFilter)
    var textToFilter : String? = null

    override fun getTextSelected(): String {
        return getNameWithCode()
    }

    fun getTextFilter(): String {
        return textToFilter ?: ""
    }

    fun getAddress(): String {
        val builder = StringBuilder()

        if (StringUtils.isNotEmpty(city)) {
            builder.append("$city - ")
        }

        if (StringUtils.isNotEmpty(state)) {
            builder.append("$state - ")
        }

        if (StringUtils.isNotEmpty(district)) {
            builder.append("$district")
        }

        return StringUtils.removeEnd(StringUtils.normalizeSpace(builder.toString()), "-")
    }

    fun getNameWithCode(): String {
        val builder = StringBuilder()

        if (StringUtils.isNotEmpty(code)) {
            builder.append("$code - ")
        }

        if (StringUtils.isNotEmpty(name)) {
            builder.append("$name")
        }

        return StringUtils.removeEnd(StringUtils.normalizeSpace(builder.toString()), "-")
    }
}