package com.unilever.julia.components.enums

import android.content.res.TypedArray

enum class IconEnums(var iconHexa: String) {
    FOODS_2("e912"),
    HOME_CARE("e913"),
    ICE_CREAM("e914"),
    ATENCAO("e91c"),
    ATRASADO("e91b"),
    PROJETO("e91a"),
    AGENDA("e900"),
    REGRAS_OURO("e90e"),
    TITULO("e901"),
    CLIENTE("e905"),
    FATURADO("e907"),
    PENDENTE("e90c"),
    ANULADO("e902"),
    VALOR("e911"),
    CAIXA("e903"),
    CONFIGURACAO("e906"),
    SAIR("e90b"),
    TERMOSDEUSO("e910"),
    OUTLOOK("e916"),
    CALENDARIO("e917"),
    HORARIO("e918"),
    USUARIO("e919"),
    LIKE("e921"),
    LIKE_SELECT("e920"),
    DISLIKE("e91f"),
    DISLIKE_SELECT("e91e"),
    CARTEIRA("e922"),
    MAPA("e925"),
    LOJA("e924"),
    COD_BARRAS("e923"),
    IPV("e926"),
    NOTIFICACAO("e929"),
    ICON_KG("e931"),
    ICON_CLAP("e933"),
    ICON_WAIT("e934"),
    ICON_ATENTION("e935"),
    ICON_ERROR_FACE("e936"),
    ICON_OUT_SERVER("e937");

    companion object {
        fun getIconEnumByStyleable(typedArray : TypedArray, indexStyleable : Int): IconEnums {
            var iconEnum = FOODS_2
            val indexIconEnum = typedArray.getInt(indexStyleable, -1)
            if (indexIconEnum >= 0) {
                iconEnum = values()[indexIconEnum]
            }
            return iconEnum
        }
    }
}