package com.unilever.julia.data.model

import com.unilever.julia.components.enums.ComponentsType
import com.unilever.julia.components.model.IComponentsModel

class ButtonModel : IComponentsModel {

    var text : String = ""

    val items : MutableList<Item> = arrayListOf()

    data class Item(var icon: String, var color: String, var text: String, var intencao: String)

    override fun getType(): ComponentsType {
        return ComponentsType.BUTTON
    }
}