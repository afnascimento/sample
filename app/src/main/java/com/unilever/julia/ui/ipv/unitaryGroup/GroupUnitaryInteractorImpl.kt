package com.unilever.julia.ui.ipv.unitaryGroup

import com.google.gson.Gson
import com.unilever.julia.ui.base.*

import com.unilever.julia.data.*
import com.unilever.julia.data.model.goods.Good
import com.unilever.julia.data.model.goods.Goods
import com.unilever.julia.data.model.ipv.IpvContext
import com.unilever.julia.exception.EmptyDataException
import io.reactivex.Observable

import javax.inject.Inject

class GroupUnitaryInteractorImpl
@Inject constructor(mDataManager: DataManager) : BaseInteractorImpl(mDataManager),
    GroupUnitaryInteractor {

    private val mGson = Gson()

    override fun getIpvGoods(sessionCode: String, ipvContext: IpvContext): Observable<Good> {
        return dataManager().juliaApi().getIpvConversations(
            sessionCode, ipvContext.code?: "", ipvContext.value ?: ""
        ).flatMap { conversations ->
            val model = mGson.fromJson<Goods>(
                conversations.getAnswer().technicalText,
                Goods::class.java
            )
            if (model.goods.isNullOrEmpty()) {
                throw EmptyDataException()
            }

            return@flatMap Observable.just(model.goods?.get(0))
        }
    }
}