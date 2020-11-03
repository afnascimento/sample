package com.unilever.julia.data.model

import com.unilever.julia.components.enums.ComponentsType
import com.unilever.julia.components.ButtonHorizontal
import com.unilever.julia.components.model.IComponentsModel

class ButtonHorizontalModel : IComponentsModel {

    var text : String = ""

    val items = arrayListOf<ButtonHorizontal.Item>()

    override fun getType(): ComponentsType {
        return ComponentsType.BUTTON_HORIZONTAL
    }
}