package com.unilever.julia.ui.ipv.management

import com.google.gson.Gson
import com.unilever.julia.ui.base.*

import com.unilever.julia.data.*
import com.unilever.julia.data.model.ipv.IpvContext
import com.unilever.julia.data.model.ipv.manager.IpvManagement
import com.unilever.julia.data.model.ipv.manager.IpvSummary
import io.reactivex.Observable

import javax.inject.Inject

class ManagementInteractorImpl
@Inject constructor(mDataManager: DataManager) : BaseInteractorImpl(mDataManager), ManagementInteractor {

    private val mGson = Gson()

    override fun getIpvManagement(sessionCode: String, ipvContext: IpvContext): Observable<IpvSummary> {
        return dataManager().juliaApi().getIpvConversations(
            sessionCode, ipvContext.code?: "", ipvContext.value ?: ""
        ).flatMap { conversations ->
            val model = mGson.fromJson<IpvManagement>(
                conversations.getAnswer().technicalText,
                IpvManagement::class.java
            )
            return@flatMap Observable.just(model.summary)
        }
    }
}