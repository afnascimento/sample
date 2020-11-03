package com.unilever.julia.ui.ipv

import com.unilever.julia.components.IpvTabLayout
import com.unilever.julia.data.model.ipv.*
import com.unilever.julia.ui.base.*
import io.reactivex.Observable

import javax.inject.Inject

class IpvPresenterImpl<V : IpvView, I : IpvInteractor>
@Inject constructor(mView: V, mInteractor: I) : BasePresenterImpl<V, I>(mView, mInteractor), IpvPresenter<V, I> {

    private var mIpvContext : IpvContext = IpvContext()

    private var mItems = arrayListOf<IpvItem>()

    override fun init(context: IpvContext, items: List<IpvItem>) {
        mIpvContext = context

        mItems.clear()
        mItems.addAll(items)

        val target = arrayListOf(
            IpvType.FASEAMENTO,
            IpvType.POSITIVACAO,
            IpvType.INOVACAO,
            IpvType.OFERTAS,
            IpvType.PRIORITARIOS,
            IpvType.CATEGORIA_FOCO
        )

        val tabs = arrayListOf<IpvTabLayout.Item>()
        val types = arrayListOf<IpvType>()
        for (type in target) {
            val tab = getTabItemByName(type, items)
            if (tab != null) {
                tabs.add(tab)
                types.add(type)
            }
        }

        getView().addTabs(tabs)
        getView().addPages(types)
    }

    override fun getIpvModel(type: IpvType): IpvItem? {
        val filter = mItems.filter {
            val ipvType = it.getIpvType()
            return@filter (ipvType != null && ipvType == type)
        }
        if (filter.isNotEmpty()) {
            return filter[0]
        }
        return null
    }

    fun getTabItemByName(type: IpvType, items: List<IpvItem>): IpvTabLayout.Item? {
        val filter = items.filter {
            val ipvType = it.getIpvType()
            return@filter (ipvType != null && ipvType == type)
        }
        if (filter.isNotEmpty()) {
            val item : IpvItem = filter[0]
            return IpvTabLayout.Item(item.colorCode ?: "", item.type ?: "")
        }
        return null
    }

    override fun getIpvFaseamento(sessionCode: String): Observable<IpvFaseamento> {
        return getInteractor().getIpvFaseamento(sessionCode, mIpvContext)
    }

    override fun getIpvPrioritiesClients(sessionCode: String): Observable<List<IpvClient>> {
        return getInteractor().getIpvPrioritiesClients(sessionCode, mIpvContext)
    }

    override fun getFocusClients(sessionCode: String): Observable<List<IpvClient>> {
        return getInteractor().getFocusClients(sessionCode, mIpvContext)
    }

    override fun getIpvOffers(sessionCode: String): Observable<IpvOffers> {
        return getInteractor().getIpvOffers(sessionCode, mIpvContext)
    }

    override fun getPositivationClients(sessionCode: String): Observable<List<IpvClient>> {
        return getInteractor().getPositivationClients(sessionCode, mIpvContext)
    }

    override fun getInnovationClients(sessionCode: String): Observable<List<IpvClient>> {
        return getInteractor().getInnovationClients(sessionCode, mIpvContext)
    }

    override fun onClientFocusClick(ipvClient: IpvClient) {
        getView().redirectGroupUnitaryActivity(ipvClient, ipvClient.context!!, "")
    }

    override fun onClientPrioritiesClick(ipvClient: IpvClient) {
        getView().redirectCategoriesPrioritiesActivity(ipvClient)
    }

    override fun onClientInnovationClick(ipvClient: IpvClient) {
        getView().redirectIpvProductsActivity(ipvClient, ipvClient.context!!, "")
    }
}