package com.unilever.julia.data.model.parser

import org.apache.commons.lang3.StringUtils

enum class ParserModelTypes(private var titles: List<String>) {

    BOLETIM_VENDAS(listOf("01_BOLETIM_VENDAS")),
    BUTTON(listOf("BUTTON")),
    BUTTON_V2(listOf("BUTTON_V2", "00_SAIR")),
    DISAMBIGUATION(listOf("DISAMBIGUATION")),
    DISAMBIGUATION_MEU_TERRITORIO(listOf("07_STATUS_PEDIDO_CARTEIRA", "CARD_CARTEIRA", "BUSCA_TERRITORIO")),
    DISAMBIGUATION_OUTRO_TERRITORIO(listOf("BUSCA_TERRITORIO_PERGUNTA")),
    DISAMBIGUATION_POR_CLIENTE(listOf("BUSCA_CLIENTE")),
    DISAMBIGUATION_COD_PEDIDO(listOf("09_STATUS_PEDIDO_COD")),
    CARD_INOVACAO(listOf("CARD_INOVACAO")),
    CARD_AGENDA(listOf("CARD_AGENDA")),
    CARD_IPV(listOf("CARD_IPV")),
    AGENDA_OUTLOOK(listOf("21_AGENDA_OUTLOOK_ADICIONAR")),
    ADICIONAR_AGENDA(listOf("ADICIONAR_AGENDA")),
    CARD_STATUS_PEDIDOS(listOf("CARD_STATUS_PEDIDOS")),
    PEDIDOS_CLIENTE(listOf("TEXT_STATUS_PEDIDO_CLIENTE")),
    FEEDBACK(listOf("02_DISLIKE")),
    SUGESTAO_ENVIO(listOf("01_SUGESTAO")),
    SUGESTAO_RESPOSTA(listOf("02_SUGESTAO_RESPOSTA")),
    JULIA_ANSWER(listOf("JULIA_ANSWER")),
    CHITCHAT(listOf("CHITCHAT"));

    companion object {
        fun getType(title: String): ParserModelTypes {
            for (it in values()) {
                if (StringUtils.equalsAnyIgnoreCase(title, *it.titles.toTypedArray())) {
                    return it
                }
            }
            return CHITCHAT
        }
    }
}