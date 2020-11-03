package com.unilever.julia.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.unilever.julia.data.database.EntitiesMetadata
import com.unilever.julia.data.database.entity.Territory
import io.reactivex.Flowable

@Dao
interface TerritoryRoomDao {

    @Query("DELETE FROM " + EntitiesMetadata.Territory.tableName)
    fun deleteAllTerritories()

    @Query("SELECT * FROM " + EntitiesMetadata.Territory.tableName)
    fun getAllTerritories(): Flowable<MutableList<Territory>>

    @Insert
    fun insertTerritories(vararg territory: Territory)
}