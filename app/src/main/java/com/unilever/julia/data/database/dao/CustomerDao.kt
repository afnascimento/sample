package com.unilever.julia.data.database.dao

import com.unilever.julia.data.database.entity.Customer
import io.reactivex.Observable

interface CustomerDao {

    fun getAllCustomers(): Observable<MutableList<Customer>>

    fun insertCustomers(customers: MutableList<Customer>)

    fun deleteAllCustomers()
}