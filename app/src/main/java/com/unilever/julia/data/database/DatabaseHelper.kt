package com.unilever.julia.data.database

import com.unilever.julia.data.database.dao.CustomerDao
import com.unilever.julia.data.database.dao.TerritoryDao

interface DatabaseHelper {
    fun customerDao(): CustomerDao
    fun territoryDao(): TerritoryDao
}