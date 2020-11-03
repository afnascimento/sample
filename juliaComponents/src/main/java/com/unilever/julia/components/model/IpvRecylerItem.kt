package com.unilever.julia.components.model

interface IpvRecylerItem {

    fun getIconCode(): String

    fun getIconColor(): String

    fun getTextRegular(): String

    fun isTextRegularVisible(): Boolean

    fun getTextBold(): String

    fun isTextBoldVisible(): Boolean

    fun isButtonClickVisible(): Boolean

    fun getTextFilter(): String
}