package com.unilever.julia.data.model

import com.unilever.julia.components.enums.ComponentsType
import com.unilever.julia.components.model.IComponentsModel

class JuliaAnswerModel : IComponentsModel {

    var text: String = ""

    var isCopyTextOnClick = false

    constructor(text: String) {
        this.text = text
    }

    constructor(text: String, isCopyTextOnClick: Boolean) {
        this.text = text
        this.isCopyTextOnClick = isCopyTextOnClick
    }

    override fun getType(): ComponentsType {
        return ComponentsType.JULIA_ANSWER
    }
}