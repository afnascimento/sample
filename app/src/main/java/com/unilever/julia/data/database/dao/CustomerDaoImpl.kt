package com.unilever.julia.data.database.dao

import com.unilever.julia.data.database.entity.Customer
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class CustomerDaoImpl(private val mDao: CustomerRoomDao) : CustomerDao {

    override fun deleteAllCustomers() {
        mDao.deleteAllCustomers()
        /*
        return Observable.fromCallable {
            mDao.deleteAllCustomers()
            return@fromCallable true
        }
        */
    }

    override fun getAllCustomers(): Observable<MutableList<Customer>> {
        return mDao.getAllCustomers()
            .toObservable()
            .debounce(500, TimeUnit.MILLISECONDS)
            .onErrorReturn {
                arrayListOf()
            }
    }

    override fun insertCustomers(customers: MutableList<Customer>) {
        deleteAllCustomers()
        mDao.insertCustomers(*customers.toTypedArray())
        /*
        return Observable.fromCallable {
            if (customers.isNotEmpty()) {
                mDao.deleteAllCustomers()
                mDao.insertCustomers(*customers.toTypedArray())
            }
            return@fromCallable true
        }
        */
    }
}