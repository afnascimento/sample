package com.unilever.julia.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.unilever.julia.data.database.EntitiesMetadata
import com.unilever.julia.ui.component.IAutoCompleteModel

@Entity(tableName = EntitiesMetadata.Territory.tableName)
class Territory : IAutoCompleteModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = EntitiesMetadata.Territory.id)
    var id : Int? = null

    @ColumnInfo(name = EntitiesMetadata.Territory.code)
    var code : String? = null

    override fun getTextSelected(): String {
        return code ?: ""
    }
}