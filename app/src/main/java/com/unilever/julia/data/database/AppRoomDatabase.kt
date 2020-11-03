package com.unilever.julia.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.unilever.julia.data.database.dao.CustomerRoomDao
import com.unilever.julia.data.database.dao.TerritoryRoomDao
import com.unilever.julia.data.database.entity.Customer
import com.unilever.julia.data.database.entity.Territory

@Database(entities = arrayOf(Customer::class, Territory::class), version = 1, exportSchema = false)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun customerDao(): CustomerRoomDao

    abstract fun territoryDao(): TerritoryRoomDao

    companion object {
        fun getInstance(context: Context): AppRoomDatabase {
            return Room
                .databaseBuilder(context, AppRoomDatabase::class.java, "julia-db")
                .fallbackToDestructiveMigration()
                //.allowMainThreadQueries() // not using in production
                .build()
        }
    }
}