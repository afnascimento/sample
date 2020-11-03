package com.unilever.julia.data.model

import com.unilever.julia.components.enums.ComponentsType
import com.unilever.julia.components.model.IComponentsModel

class FeedbackOptionsModel : IComponentsModel {

    override fun getType(): ComponentsType {
        return ComponentsType.FEEDBACK_OPTIONS
    }

    var text : String = ""

    val items = arrayListOf<Item>()

    data class Item(var title: String, var intencao: String)
}