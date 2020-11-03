package com.unilever.julia.ui.menu.configuracao

import com.unilever.julia.ui.base.BaseView

interface ConfiguracaoView : BaseView {
    fun setConfigTextToSpeech(checked: Boolean)
    fun setConfigOutlook(checked: Boolean)
    fun setConfigIPV(checked: Boolean)
}