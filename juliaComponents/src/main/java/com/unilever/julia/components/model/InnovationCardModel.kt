package com.unilever.julia.components.model

import com.google.gson.annotations.SerializedName

abstract class InnovationCardModel : IComponentsModel {

    var text : String = ""

    class Card {
        @SerializedName("icon")
        var icon: String = ""

        @SerializedName("icon2")
        var icon2: String = ""

        @SerializedName("color")
        var color: String = ""

        @SerializedName("text")
        var text: String = ""

        @SerializedName("projetos")
        var projects : Int = 0

        @SerializedName("entidades")
        var entities : String = ""
    }

    class ContentReference {

        var code : String = ""

        var marcaId : String = ""

        var categoriaId : String = ""

        var type : String = ""

        var commodityId : String = ""

        var sessionCode : String = ""
    }
}