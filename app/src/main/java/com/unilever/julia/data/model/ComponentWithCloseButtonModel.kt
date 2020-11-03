package com.unilever.julia.data.model

import com.unilever.julia.components.model.ButtonClickListenerModel

open class ComponentWithCloseButtonModel {

    var closeButton = ButtonClickListenerModel()

    var events = EventsWidget()

    class EventsWidget {
        var showButtons = true
        var textCloseButton = ""
    }

    fun showCloseButton(textCloseButton : String) {
        events.showButtons = false
        events.textCloseButton = textCloseButton
    }
}