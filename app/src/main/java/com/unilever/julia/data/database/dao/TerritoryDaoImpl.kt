package com.unilever.julia.data.database.dao

import com.unilever.julia.data.database.entity.Territory
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class TerritoryDaoImpl(private val mDao: TerritoryRoomDao) : TerritoryDao {

    override fun deleteAllTerritories() {
        mDao.deleteAllTerritories()
        /*
        return Observable.fromCallable {
            mDao.deleteAllTerritories()
            return@fromCallable true
        }
        */
    }

    override fun getAllTerritories(): Observable<MutableList<Territory>> {
        return mDao.getAllTerritories()
            .toObservable()
            .debounce(500, TimeUnit.MILLISECONDS)
            .onErrorReturn {
                arrayListOf()
            }
    }

    override fun insertTerritories(territories: MutableList<Territory>) {
        deleteAllTerritories()
        mDao.insertTerritories(*territories.toTypedArray())
        /*
        return Observable.fromCallable {
            if (territories.isNotEmpty()) {
                mDao.deleteAllTerritories()
                mDao.insertTerritories(*territories.toTypedArray())
            }
            return@fromCallable true
        }
        */
    }
}