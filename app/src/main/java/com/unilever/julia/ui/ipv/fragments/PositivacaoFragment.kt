package com.unilever.julia.ui.ipv.fragments

import com.unilever.julia.data.model.ipv.IpvItem
import com.unilever.julia.data.model.ipv.IpvClient
import io.reactivex.Observable

class PositivacaoFragment : AbstractClientsFragment() {

    override fun isButtonRightVisible(): Boolean {
        return false
    }

    override fun onItemClick(item: IpvClient) {
    }

    override fun isScoreVisble(): Boolean {
        return false
    }

    override fun getIpvModel(): IpvItem? {
        return null
    }

    override fun getIpvClients(): Observable<List<IpvClient>> {
        return mPresenter.getPositivationClients("")
    }

    override fun isButtonRoundVisible(): Boolean {
        return false
    }
}