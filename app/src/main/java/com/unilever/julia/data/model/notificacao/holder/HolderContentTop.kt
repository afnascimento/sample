package com.unilever.julia.data.model.notificacao.holder

import com.unilever.julia.data.model.notificacao.Type
import java.util.*

data class HolderContentTop(
    var date : Date? = null,
    var title : String = "",
    var description : String = ""
) : AttachedViewType {

    override fun getViewType(): Type {
        return Type.CONTENT_TOP
    }
}