package com.unilever.julia.ui.ipv.management

import com.unilever.julia.R
import com.unilever.julia.components.LoadView
import com.unilever.julia.components.model.IpvManagementCard
import com.unilever.julia.data.model.ipv.IpvContext
import com.unilever.julia.data.model.ipv.IpvItem
import com.unilever.julia.data.model.ipv.manager.IpvMember
import com.unilever.julia.data.model.ipv.manager.IpvSummary
import com.unilever.julia.ui.base.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject

class ManagementPresenterImpl<V : ManagementView, I : ManagementInteractor>
@Inject constructor(mView: V, mInteractor: I) : BasePresenterImpl<V, I>(mView, mInteractor), ManagementPresenter<V, I> {

    override fun getIpvManagement(sessionCode: String, ipvContext: IpvContext) {
        getView().addDisposable(
            getInteractor().getIpvManagement(sessionCode, ipvContext)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    getView().showLoadView()
                }
                .subscribeWith(object : DisposableObserver<IpvSummary>() {
                    override fun onNext(value: IpvSummary) {
                        val header = getHeader(value)
                        getView().setTitle(getView().getTextString(R.string.ipv_title, header.role.sufix))
                        getView().setHintSearch(getView().getTextString(R.string.ipv_hint, header.role.sufix))
                        getView().setHeader(header)
                        getView().addItems(getMembersManagement(value.members ?: emptyList()))
                    }

                    override fun onComplete() {
                        getView().hideLoadView()
                    }

                    override fun onError(e: Throwable) {
                        getView().onErrorHandler(e, LoadView.OnClickListener { getIpvManagement(sessionCode, ipvContext) })
                    }
                })
        )
    }

    private fun getHeader(value: IpvSummary): IpvManagementCard {
        val card = IpvManagementCard()
        card.code = value.code ?: ""
        card.name = value.description ?: ""
        card.meta = IpvManagementCard.Item(
            value.ipv?.target?.percentage ?: 0.0,
            value.ipv?.target?.colorCode ?: "",
            value.ipv?.target?.type ?: ""
        )
        card.score = IpvManagementCard.Item(
            value.ipv?.score?.percentage ?: 0.0,
            value.ipv?.score?.colorCode ?: "",
            value.ipv?.score?.type ?: ""
        )
        card.role = IpvManagementCard.getRole(value.role ?: "")
        //card.context = null
        val items : MutableList<IpvManagementCard.Item> = arrayListOf()
        for (it in value.ipv?.items ?: emptyList()) {
            items.add(IpvManagementCard.Item(
                it.percentage ?: 0.0,
                it.colorCode ?: "",
                it.type ?: ""
            ))
        }
        card.items = items
        return card
    }

    private fun getMembersManagement(members: List<IpvMember>): List<IpvManagementCard> {
        val items : MutableList<IpvManagementCard> = arrayListOf()
        for (it in members) {
            items.add(getMember(it))
        }
        return items
    }

    private fun getMember(value: IpvMember): IpvManagementCard {
        val card = IpvManagementCard()
        card.code = value.code ?: ""
        card.name = value.description ?: ""
        card.meta = IpvManagementCard.Item(
            value.ipv?.target?.percentage ?: 0.0,
            value.ipv?.target?.colorCode ?: "",
            value.ipv?.target?.type ?: ""
        )
        card.score = IpvManagementCard.Item(
            value.ipv?.score?.percentage ?: 0.0,
            value.ipv?.score?.colorCode ?: "",
            value.ipv?.score?.type ?: ""
        )
        card.role = IpvManagementCard.getRole(value.role ?: "")
        card.context = IpvManagementCard.Context(
            value.context?.code ?: "",
            value.context?.value ?: ""
        )

        val items : MutableList<IpvManagementCard.Item> = arrayListOf()
        for (it in value.ipv?.items ?: emptyList()) {
            items.add(IpvManagementCard.Item(
                it.percentage ?: 0.0,
                it.colorCode ?: "",
                it.type ?: ""
            ))
        }
        card.items = items
        return card
    }

    override fun onClickNextContext(management: IpvManagementCard) {
        val isManagement = management.context?.isManagement() ?: false
        if (isManagement) {
            getView().redirectManagementActivity(IpvContext(management.context?.code , management.context?.value))
            return
        }
        val items : MutableList<IpvItem> = arrayListOf()
        for (it in management.getItemsSorted()) {
            items.add(IpvItem(it.percentage, it.colorCode, it.description))
        }
        getView().redirectIpvActivity(IpvContext(management.context?.code, management.context?.value), items)
    }
}