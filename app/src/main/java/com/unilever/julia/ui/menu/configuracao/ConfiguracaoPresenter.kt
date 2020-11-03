package com.unilever.julia.ui.menu.configuracao

import com.unilever.julia.ui.base.BasePresenter

interface ConfiguracaoPresenter<V: ConfiguracaoView, I: ConfiguracaoInteractor> : BasePresenter<V, I> {
    fun saveTextToSpeech(enabled: Boolean)
    fun saveOutlook(enabled: Boolean)
    fun saveIPV(enabled: Boolean)
    fun initConfiguration()
}