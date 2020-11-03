package com.unilever.julia.components.model

class ButtonClickListenerModel {

    var text: String = ""
    var intencao: String = ""
    var eventAnalytics: String = ""
    var paramAnalytics: String = ""

    constructor()

    constructor(text: String, intencao: String, eventAnalytics: String, paramAnalytics: String) {
        this.text = text
        this.intencao = intencao
        this.eventAnalytics = eventAnalytics
        this.paramAnalytics = paramAnalytics
    }
}