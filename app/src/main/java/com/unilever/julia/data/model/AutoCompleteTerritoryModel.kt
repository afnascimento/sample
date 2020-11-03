package com.unilever.julia.data.model

import com.unilever.julia.data.database.entity.Territory
import com.unilever.julia.components.enums.ComponentsType
import com.unilever.julia.components.model.IComponentsModel

class AutoCompleteTerritoryModel : IComponentsModel, ComponentWithCloseButtonModel() {

    override fun getType(): ComponentsType {
        return ComponentsType.AUTO_COMPLETE_TERRITORIES
    }

    var text = ""

    var items: MutableList<Territory> = arrayListOf()

    var intent : String = ""

    var hint : String = ""
}