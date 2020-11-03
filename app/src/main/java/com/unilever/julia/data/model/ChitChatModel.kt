package com.unilever.julia.data.model

import com.unilever.julia.components.enums.ComponentsType
import com.unilever.julia.components.model.IComponentsModel

class ChitChatModel : IComponentsModel {

    override fun getType(): ComponentsType {
        return ComponentsType.CHIT_CHAT
    }

    var text : String = ""

    val items = arrayListOf<Item>()

    data class Item(var text: String, var url: String)
}