package com.unilever.julia.data.database.dao

import com.unilever.julia.data.database.entity.Territory
import io.reactivex.Observable

interface TerritoryDao {

    fun getAllTerritories(): Observable<MutableList<Territory>>

    fun insertTerritories(territories: MutableList<Territory>)

    fun deleteAllTerritories()
}