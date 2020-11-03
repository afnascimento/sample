package com.unilever.julia.data.model

import com.unilever.julia.data.database.entity.Customer
import com.unilever.julia.components.enums.ComponentsType
import com.unilever.julia.components.model.IComponentsModel

class AutoCompleteCustomerModel : IComponentsModel, ComponentWithCloseButtonModel() {

    override fun getType(): ComponentsType {
        return ComponentsType.AUTO_COMPLETE_CUSTOMERS
    }

    var text = ""

    var hint : String = ""

    var items: MutableList<Customer> = arrayListOf()

    var intent : String = ""
}