package com.unilever.julia.ui.boletim.multiple

import com.google.gson.Gson
import com.unilever.julia.ui.base.*

import com.unilever.julia.data.*
import com.unilever.julia.data.model.Conversations
import com.unilever.julia.data.model.bulletin.BulletinsMultipleFiltersResponse
import com.unilever.julia.components.boletim.AttendanceFilter
import com.unilever.julia.exception.EmptyDataException
import io.reactivex.Observable

import javax.inject.Inject

class BoletimMultipleInteractorImpl
@Inject constructor(mDataManager: DataManager) : BaseInteractorImpl(mDataManager), BoletimMultipleInteractor {

    private val mGson = Gson()

    override fun getBulletinsMultipleFilters(type: BulletinsMultipleFiltersResponse.Type,
                                             attendanceFilter: AttendanceFilter
    ): Observable<List<String>> {
        return dataManager().juliaApi().getBulletinsMultipleFilters("", attendanceFilter)
            .flatMap { conversations: Conversations ->
                val response = mGson.fromJson(
                    conversations.getAnswer().technicalText ?: "",
                    BulletinsMultipleFiltersResponse::class.java
                )

                val list : MutableList<String> = arrayListOf()

                when (type) {
                    BulletinsMultipleFiltersResponse.Type.CANAIS -> {
                        list.addAll(response.salesBulletinsFilters?.helpChannel ?: arrayListOf())
                    }
                    BulletinsMultipleFiltersResponse.Type.CATEGORIAS -> {
                        list.addAll(response.salesBulletinsFilters?.category ?: arrayListOf())
                    }
                    BulletinsMultipleFiltersResponse.Type.CLIENTES -> {
                        list.addAll(response.salesBulletinsFilters?.customer ?: arrayListOf())
                    }
                    BulletinsMultipleFiltersResponse.Type.COMMODITIES -> {
                        list.addAll(response.salesBulletinsFilters?.commodity ?: arrayListOf())
                    }
                    BulletinsMultipleFiltersResponse.Type.MARCAS -> {
                        list.addAll(response.salesBulletinsFilters?.brand ?: arrayListOf())
                    }
                }

                if (list.isEmpty()) {
                    throw EmptyDataException()
                }

                return@flatMap Observable.just(list)
            }
    }
}