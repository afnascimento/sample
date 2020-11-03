package com.unilever.julia.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.unilever.julia.data.database.EntitiesMetadata
import com.unilever.julia.data.database.entity.Customer
import io.reactivex.Flowable

@Dao
interface CustomerRoomDao {

    @Query("DELETE FROM " + EntitiesMetadata.Customer.tableName)
    fun deleteAllCustomers()

    @Query("SELECT * FROM " + EntitiesMetadata.Customer.tableName)
    fun getAllCustomers(): Flowable<MutableList<Customer>>

    @Insert
    fun insertCustomers(vararg customers: Customer)
}