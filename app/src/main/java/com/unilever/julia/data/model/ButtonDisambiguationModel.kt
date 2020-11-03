package com.unilever.julia.data.model

import com.unilever.julia.components.model.ButtonClickListenerModel
import com.unilever.julia.components.enums.ComponentsType
import com.unilever.julia.components.model.IComponentsModel
import org.apache.commons.lang3.StringUtils

class ButtonDisambiguationModel : IComponentsModel, ComponentWithCloseButtonModel() {

    override fun getType(): ComponentsType {
        return ComponentsType.BUTTON_DISAMBIGUATION
    }

    var text : String = ""

    val items = arrayListOf<Item>()

    val options = arrayListOf<Option>()

    fun getCloseIntent(): ButtonClickListenerModel {
        val optionClose = getOptionCloseIntent()
        val model = ButtonClickListenerModel()
        model.eventAnalytics = "disambiguation"
        model.paramAnalytics = "botao_close"
        model.text = optionClose?.text ?: ""
        model.intencao = optionClose?.intent ?: ""
        return model
    }

    private fun selectorEqualsTitle(it: Option): Boolean = StringUtils.equalsIgnoreCase(it.title, "SAIR")

    fun getOptionCloseIntent(): Option? {
        val filter = options.filter { selectorEqualsTitle(it) }
        if (filter.isNotEmpty()) {
            return filter[0]
        }
        return null
    }

    fun getOptionsWithoutCloseIntent(): MutableList<Option> {
        return options.filterNot { selectorEqualsTitle(it) }.toMutableList()
    }

    class Option {
        var icon: String = ""
        var color: String = ""
        var title: String = ""
        var intent: String = ""
        var event: String = ""
        var param: String = ""
        var text: String = ""
    }

    class Item {
        var icon: String = ""
        var color: String = ""
        var clickButton = ButtonClickListenerModel()
        var closeButton = ButtonClickListenerModel()

        constructor()

        constructor(icon: String, color: String,
                    clickButton: ButtonClickListenerModel,
                    closeButton : ButtonClickListenerModel
        ) {
            this.icon = icon
            this.color = color
            this.clickButton = clickButton
            this.closeButton = closeButton
        }
    }
}