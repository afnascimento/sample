package com.unilever.julia.data.model

import com.unilever.julia.components.enums.ComponentsType
import com.unilever.julia.components.model.IComponentsModel

class SugestaoModel : IComponentsModel {

    var code = ""

    var text = ""

    constructor(code: String, text: String) {
        this.code = code
        this.text = text
    }

    override fun getType(): ComponentsType {
        return ComponentsType.CHIT_CHAT
    }
}