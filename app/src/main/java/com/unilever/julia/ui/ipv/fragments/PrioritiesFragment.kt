package com.unilever.julia.ui.ipv.fragments

import com.unilever.julia.data.model.ipv.IpvItem
import com.unilever.julia.data.model.ipv.IpvType
import com.unilever.julia.data.model.ipv.IpvClient
import io.reactivex.Observable

class PrioritiesFragment : AbstractClientsFragment() {

    override fun isButtonRightVisible(): Boolean {
        return true
    }

    override fun onItemClick(item: IpvClient) {
        mView.onClientPrioritiesClick(item)
    }

    override fun isScoreVisble(): Boolean {
        return true
    }

    override fun getIpvModel(): IpvItem? {
        return mPresenter.getIpvModel(IpvType.PRIORITARIOS)
    }

    override fun getIpvClients(): Observable<List<IpvClient>> {
        return mPresenter.getIpvPrioritiesClients("")
    }

    override fun isButtonRoundVisible(): Boolean {
        return true
    }
}