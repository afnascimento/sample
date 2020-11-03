package com.unilever.julia.ui.tutorial

import com.unilever.julia.data.model.News
import com.unilever.julia.ui.base.BaseView

interface TutorialView : BaseView {
    fun addItems(items: MutableList<News.Item>)
}