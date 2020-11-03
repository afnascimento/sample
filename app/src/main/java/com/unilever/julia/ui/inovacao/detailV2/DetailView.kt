package com.unilever.julia.ui.inovacao.detailV2

import androidx.annotation.DrawableRes
import com.unilever.julia.ui.base.*
import com.unilever.julia.ui.inovacao.detailV2.model.PairFragment

interface DetailView : BaseView {

    fun initTabs(items: MutableList<PairFragment>)
    fun setTitle(title: String)
    fun setImageCardLogo(url: String)
    fun resumeFragmentActual()
    fun startDownload(url: String)
    fun setTitleToolbar(title: String)
    fun setImageBanner(url: String, @DrawableRes placeholder: Int)
}