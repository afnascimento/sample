package com.unilever.julia.data.database

import com.unilever.julia.data.database.dao.CustomerDao
import com.unilever.julia.data.database.dao.CustomerDaoImpl
import com.unilever.julia.data.database.dao.TerritoryDao
import com.unilever.julia.data.database.dao.TerritoryDaoImpl

class DatabaseHelperImpl(database : AppRoomDatabase) : DatabaseHelper {

    private val customerDao : CustomerDao = CustomerDaoImpl(database.customerDao())

    private val territoryDao : TerritoryDao = TerritoryDaoImpl(database.territoryDao())

    override fun customerDao(): CustomerDao {
        return customerDao
    }

    override fun territoryDao(): TerritoryDao {
        return territoryDao
    }
}