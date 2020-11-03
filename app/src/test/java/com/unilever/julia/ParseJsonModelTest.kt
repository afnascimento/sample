package com.unilever.julia

import com.unilever.julia.data.model.*
import com.unilever.julia.utils.AgendaModelParser
import com.unilever.julia.utils.JavaUtils
import org.apache.commons.lang3.StringUtils
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Ignore
import org.junit.Test
import com.google.gson.*
import com.unilever.julia.components.model.InnovationCardVerticalModel

class ParseJsonModelTest {

    companion object {
        @JvmStatic
        private lateinit var mGson: Gson

        @BeforeClass
        @JvmStatic
        fun initialize() {
            mGson = Gson()
        }
    }

    @Ignore
    @Test
    fun parseJsonToConversations_test() {
        val json = "{\n" +
                "    \"text\": \"agenda\",\n" +
                "    \"sessionCode\": \"bec38835-2a87-4554-81e2-52db334d3138\",\n" +
                "    \"answers\": [\n" +
                "        {\n" +
                "            \"title\": \"CARD_AGENDA\",\n" +
                "            \"text\": \"É pra já! Seus compromissos para essa semana são: \",\n" +
                "            \"technicalText\": \"[{\\\"Id\\\":\\\"00T2D000003Qc6XUAS\\\",\\\"Subject\\\":\\\"Tarefas Júlia\\\",\\\"Status\\\":\\\"Not Started\\\",\\\"Priority\\\":\\\"Low\\\",\\\"OwnerId\\\":\\\"0052D0000018PqrQAE\\\",\\\"RecordTypeId\\\":\\\"0122D0000000Ab8QAE\\\",\\\"ActivityDate\\\":\\\"25/12/2018\\\"},{\\\"Id\\\":\\\"00T2D000003Qc3EUAS\\\",\\\"Subject\\\":\\\"Tarefas Júlia\\\",\\\"Status\\\":\\\"Not Started\\\",\\\"Priority\\\":\\\"Normal\\\",\\\"OwnerId\\\":\\\"0052D000001DuqxQAC\\\",\\\"RecordTypeId\\\":\\\"0122D0000000Ab8QAE\\\",\\\"ActivityDate\\\":\\\"25/12/2018\\\"},{\\\"Id\\\":\\\"00T2D000003QbxRUAS\\\",\\\"Subject\\\":\\\"Tarefas Júlia\\\",\\\"Status\\\":\\\"Not Started\\\",\\\"Priority\\\":\\\"High\\\",\\\"OwnerId\\\":\\\"0052D0000018PqrQAE\\\",\\\"RecordTypeId\\\":\\\"0122D0000000Ab8QAE\\\",\\\"ActivityDate\\\":\\\"26/12/2018\\\"},{\\\"Id\\\":\\\"00T2D000003Qc2uUAC\\\",\\\"Subject\\\":\\\"Tarefas Júlia\\\",\\\"Status\\\":\\\"Not Started\\\",\\\"Priority\\\":\\\"High\\\",\\\"OwnerId\\\":\\\"0052D000001DuqxQAC\\\",\\\"RecordTypeId\\\":\\\"0122D0000000Ab8QAE\\\",\\\"ActivityDate\\\":\\\"26/12/2018\\\"},{\\\"Id\\\":\\\"00T2D000003Qc3JUAS\\\",\\\"Subject\\\":\\\"Tarefas Júlia\\\",\\\"Status\\\":\\\"Not Started\\\",\\\"Priority\\\":\\\"Low\\\",\\\"OwnerId\\\":\\\"0052D000001DuqxQAC\\\",\\\"RecordTypeId\\\":\\\"0122D0000000Ab8QAE\\\",\\\"ActivityDate\\\":\\\"26/12/2018\\\"},{\\\"Id\\\":\\\"00T2D000003Qc3OUAS\\\",\\\"Subject\\\":\\\"Tarefas Júlia\\\",\\\"Status\\\":\\\"Not Started\\\",\\\"Priority\\\":\\\"Low\\\",\\\"OwnerId\\\":\\\"0052D000001DuqxQAC\\\",\\\"RecordTypeId\\\":\\\"0122D0000000Ab8QAE\\\",\\\"ActivityDate\\\":\\\"26/12/2018\\\"},{\\\"Id\\\":\\\"00T2D000003Qc6cUAC\\\",\\\"Subject\\\":\\\"Tarefas Júlia\\\",\\\"Status\\\":\\\"Not Started\\\",\\\"Priority\\\":\\\"High\\\",\\\"OwnerId\\\":\\\"0052D0000018PqrQAE\\\",\\\"RecordTypeId\\\":\\\"0122D0000000Ab8QAE\\\",\\\"ActivityDate\\\":\\\"26/12/2018\\\"},{\\\"Id\\\":\\\"00T2D000003QbxQUAS\\\",\\\"Subject\\\":\\\"Tarefas Júlia\\\",\\\"Status\\\":\\\"Not Started\\\",\\\"Priority\\\":\\\"Normal\\\",\\\"OwnerId\\\":\\\"0052D0000018PqrQAE\\\",\\\"RecordTypeId\\\":\\\"0122D0000000Ab8QAE\\\",\\\"ActivityDate\\\":\\\"27/12/2018\\\"},{\\\"Id\\\":\\\"00T2D000003QbxaUAC\\\",\\\"Subject\\\":\\\"Lembrete julia\\\",\\\"Status\\\":\\\"Not Started\\\",\\\"Priority\\\":\\\"Normal\\\",\\\"Description\\\":\\\"Optional( me lembre de cortar o cabelo dia 27/12 )\\\",\\\"OwnerId\\\":\\\"005E00000044p1sIAA\\\",\\\"RecordTypeId\\\":\\\"0122D0000000Ab8QAE\\\",\\\"ActivityDate\\\":\\\"27/12/2018\\\"},{\\\"Id\\\":\\\"00T2D000003QbwcUAC\\\",\\\"Subject\\\":\\\"Lembrete julia\\\",\\\"Status\\\":\\\"Not Started\\\",\\\"Priority\\\":\\\"Normal\\\",\\\"Description\\\":\\\"Optional( me lembre de enviar relatório para Pão de Açúcar na data de 27/12/18 )\\\",\\\"OwnerId\\\":\\\"005E00000044p1sIAA\\\",\\\"RecordTypeId\\\":\\\"0122D0000000Ab8QAE\\\",\\\"ActivityDate\\\":\\\"27/12/2018\\\"},{\\\"Id\\\":\\\"00T2D000003QbxfUAC\\\",\\\"Subject\\\":\\\"Julia Api title\\\",\\\"Status\\\":\\\"Not Started\\\",\\\"Priority\\\":\\\"Normal\\\",\\\"Description\\\":\\\"Julia Api description\\\",\\\"OwnerId\\\":\\\"005E00000044p1sIAA\\\",\\\"RecordTypeId\\\":\\\"0122D0000000Ab8QAE\\\",\\\"ActivityDate\\\":\\\"28/12/2018\\\"},{\\\"Id\\\":\\\"00T2D000003Qc8JUAS\\\",\\\"Subject\\\":\\\"Lembrete julia\\\",\\\"Status\\\":\\\"Not Started\\\",\\\"Priority\\\":\\\"Normal\\\",\\\"Description\\\":\\\"Optional( me lembre de comprar papel na data de 28/12 )\\\",\\\"OwnerId\\\":\\\"0052D0000018PqrQAE\\\",\\\"RecordTypeId\\\":\\\"0122D0000000Ab8QAE\\\",\\\"ActivityDate\\\":\\\"28/12/2018\\\"},{\\\"Id\\\":\\\"00T2D000003Qc5oUAC\\\",\\\"Subject\\\":\\\"Lembrete julia\\\",\\\"Status\\\":\\\"Not Started\\\",\\\"Priority\\\":\\\"Normal\\\",\\\"Description\\\":\\\"Optional( me lembre de enviar relatório na data de 28/12/2018 )\\\",\\\"OwnerId\\\":\\\"0052D0000018PqrQAE\\\",\\\"RecordTypeId\\\":\\\"0122D0000000Ab8QAE\\\",\\\"ActivityDate\\\":\\\"28/12/2018\\\"},{\\\"Id\\\":\\\"00T2D000003Qc2zUAC\\\",\\\"Subject\\\":\\\"Tarefas Júlia\\\",\\\"Status\\\":\\\"Not Started\\\",\\\"Priority\\\":\\\"Normal\\\",\\\"OwnerId\\\":\\\"0052D000001DuqxQAC\\\",\\\"RecordTypeId\\\":\\\"0122D0000000Ab8QAE\\\",\\\"ActivityDate\\\":\\\"28/12/2018\\\"},{\\\"Id\\\":\\\"00T2D000003Qc30UAC\\\",\\\"Subject\\\":\\\"Tarefas Júlia\\\",\\\"Status\\\":\\\"Not Started\\\",\\\"Priority\\\":\\\"Low\\\",\\\"OwnerId\\\":\\\"0052D000001DuqxQAC\\\",\\\"RecordTypeId\\\":\\\"0122D0000000Ab8QAE\\\",\\\"ActivityDate\\\":\\\"28/12/2018\\\"},{\\\"Id\\\":\\\"00T2D000003Qc68UAC\\\",\\\"Subject\\\":\\\"Tarefas Júlia\\\",\\\"Status\\\":\\\"Not Started\\\",\\\"Priority\\\":\\\"High\\\",\\\"OwnerId\\\":\\\"0052D0000018PqrQAE\\\",\\\"RecordTypeId\\\":\\\"0122D0000000Ab8QAE\\\",\\\"ActivityDate\\\":\\\"28/12/2018\\\"},{\\\"Id\\\":\\\"00T2D000003QbxLUAS\\\",\\\"Subject\\\":\\\"Tarefas Júlia\\\",\\\"Status\\\":\\\"Not Started\\\",\\\"Priority\\\":\\\"High\\\",\\\"OwnerId\\\":\\\"0052D0000018PqrQAE\\\",\\\"RecordTypeId\\\":\\\"0122D0000000Ab8QAE\\\",\\\"ActivityDate\\\":\\\"29/12/2018\\\"}]\",\n" +
                "            \"audio\": null,\n" +
                "            \"options\": null\n" +
                "        }\n" +
                "    ],\n" +
                "    \"context\": {\n" +
                "        \"previous_node\": \"10\",\n" +
                "        \"intents\": [\n" +
                "            {\n" +
                "                \"name\": \"17_AGENDA_PARA_SEMANA\",\n" +
                "                \"confidence\": 0.913895547\n" +
                "            }\n" +
                "        ],\n" +
                "        \"next_node\": null,\n" +
                "        \"entities\": []\n" +
                "    }\n" +
                "}"
        val fromJson = mGson.fromJson(json, Conversations::class.java)
        Assert.assertNotNull(fromJson)
    }

    @Ignore
    @Test
    fun parseInovacaoProjetoModel_test() {
        val json = "[\n" +
                "  {\n" +
                "    \"Projeto_Inovacao\": \"SEDA SHOKO\",\n" +
                "    \"Canal\": \"TODOS\",\n" +
                "    \"Data_lancamento\": \"Março - 19\",\n" +
                "    \"Beneficio_Esperado\": \"Beneficio 1; Beneficio 2\",\n" +
                "    \"Link_TradeStory\": \"https://unilever.sharepoint.com/sites/Brazil/Pages/As-Politicas-do-Codigo.aspx\",\n" +
                "    \"Link_Material\": \"https://unilever.sharepoint.com/sites/Brazil/Pages/As-Politicas-do-Codigo.aspx\",\n" +
                "    \"Link_Ficha\": \"https://unilever.sharepoint.com/sites/Brazil/Pages/As-Politicas-do-Codigo.aspx\",\n" +
                "    \"Regras_de_Ouro\": \"Regra1;Regra2\",\n" +
                "    \"Novos_SKUs\": \"SKU1 - DESC1;SKU2 - DESC2;SKU3 - DESC3\",\n" +
                "    \"SKUs_delistados\": \"SKU4 - DESC4;SKU5 - DESC5;SKU6 - DESC6\",\n" +
                "    \"Categoria\": \"Styling\",\n" +
                "    \"Commodity\": \"PC\",\n" +
                "    \"Marca\": \"SEDA\",\n" +
                "    \"Quatidade_SKUs\": \"3\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"Projeto_Inovacao\": \"DOVE DANIKA\",\n" +
                "    \"Canal\": \"TODOS\",\n" +
                "    \"Data_lancamento\": \"Março - 19\",\n" +
                "    \"Beneficio_Esperado\": \"Beneficio 1; Beneficio 2\",\n" +
                "    \"Link_TradeStory\": \"https://unilever.sharepoint.com/sites/Brazil/Pages/As-Politicas-do-Codigo.aspx\",\n" +
                "    \"Link_Material\": \"https://unilever.sharepoint.com/sites/Brazil/Pages/As-Politicas-do-Codigo.aspx\",\n" +
                "    \"Link_Ficha\": \"https://unilever.sharepoint.com/sites/Brazil/Pages/As-Politicas-do-Codigo.aspx\",\n" +
                "    \"Regras_de_Ouro\": \"Regra1;Regra2\",\n" +
                "    \"Novos_SKUs\": \"SKU1 - DESC1;SKU2 - DESC2;SKU3 - DESC3\",\n" +
                "    \"SKUs_delistados\": \"SKU4 - DESC4;SKU5 - DESC5;SKU6 - DESC6\",\n" +
                "    \"Categoria\": \"Other Hair Care\",\n" +
                "    \"Commodity\": \"PC\",\n" +
                "    \"Marca\": \"DOVE\",\n" +
                "    \"Quatidade_SKUs\": \"3\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"Projeto_Inovacao\": \"REX TESLA\",\n" +
                "    \"Canal\": \"DIRETA\",\n" +
                "    \"Data_lancamento\": \"Março - 19\",\n" +
                "    \"Beneficio_Esperado\": \"Beneficio 1; Beneficio 2\",\n" +
                "    \"Link_TradeStory\": \"https://unilever.sharepoint.com/sites/Brazil/Pages/As-Politicas-do-Codigo.aspx\",\n" +
                "    \"Link_Material\": \"https://unilever.sharepoint.com/sites/Brazil/Pages/As-Politicas-do-Codigo.aspx\",\n" +
                "    \"Link_Ficha\": \"https://unilever.sharepoint.com/sites/Brazil/Pages/As-Politicas-do-Codigo.aspx\",\n" +
                "    \"Regras_de_Ouro\": \"Regra1;Regra2\",\n" +
                "    \"Novos_SKUs\": \"SKU1 - DESC1;SKU2 - DESC2;SKU3 - DESC3\",\n" +
                "    \"SKUs_delistados\": \"SKU4 - DESC4;SKU5 - DESC5;SKU6 - DESC6\",\n" +
                "    \"Categoria\": \"Deodorants\",\n" +
                "    \"Commodity\": \"PC\",\n" +
                "    \"Marca\": \"Rexona\",\n" +
                "    \"Quatidade_SKUs\": \"6\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"Projeto_Inovacao\": \"REX FRAGRANCIAS\",\n" +
                "    \"Canal\": \"TODOS\",\n" +
                "    \"Data_lancamento\": \"Março - 19\",\n" +
                "    \"Beneficio_Esperado\": \"Beneficio 1; Beneficio 2\",\n" +
                "    \"Link_TradeStory\": \"https://unilever.sharepoint.com/sites/Brazil/Pages/As-Politicas-do-Codigo.aspx\",\n" +
                "    \"Link_Material\": \"https://unilever.sharepoint.com/sites/Brazil/Pages/As-Politicas-do-Codigo.aspx\",\n" +
                "    \"Link_Ficha\": \"https://unilever.sharepoint.com/sites/Brazil/Pages/As-Politicas-do-Codigo.aspx\",\n" +
                "    \"Regras_de_Ouro\": \"Regra1;Regra2\",\n" +
                "    \"Novos_SKUs\": \"SKU1 - DESC1;SKU2 - DESC2;SKU3 - DESC3\",\n" +
                "    \"SKUs_delistados\": \"SKU4 - DESC4;SKU5 - DESC5;SKU6 - DESC6\",\n" +
                "    \"Categoria\": \"Deodorants\",\n" +
                "    \"Commodity\": \"PC\",\n" +
                "    \"Marca\": \"Rexona\",\n" +
                "    \"Quatidade_SKUs\": \"2\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"Projeto_Inovacao\": \"SUAVE CREME\",\n" +
                "    \"Canal\": \"CASH\",\n" +
                "    \"Data_lancamento\": \"Março - 19\",\n" +
                "    \"Beneficio_Esperado\": \"Beneficio 1; Beneficio 2\",\n" +
                "    \"Link_TradeStory\": \"https://unilever.sharepoint.com/sites/Brazil/Pages/As-Politicas-do-Codigo.aspx\",\n" +
                "    \"Link_Material\": \"https://unilever.sharepoint.com/sites/Brazil/Pages/As-Politicas-do-Codigo.aspx\",\n" +
                "    \"Link_Ficha\": \"https://unilever.sharepoint.com/sites/Brazil/Pages/As-Politicas-do-Codigo.aspx\",\n" +
                "    \"Regras_de_Ouro\": \"Regra1;Regra2\",\n" +
                "    \"Novos_SKUs\": \"SKU1 - DESC1;SKU2 - DESC2;SKU3 - DESC3\",\n" +
                "    \"SKUs_delistados\": \"SKU4 - DESC4;SKU5 - DESC5;SKU6 - DESC6\",\n" +
                "    \"Categoria\": \"HAND & BODY CARE\",\n" +
                "    \"Commodity\": \"PC\",\n" +
                "    \"Marca\": \"Rexona\",\n" +
                "    \"Quatidade_SKUs\": \"2\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"Projeto_Inovacao\": \"REXONA LIBERTADORES\",\n" +
                "    \"Canal\": \"TODOS\",\n" +
                "    \"Data_lancamento\": \"Março - 19\",\n" +
                "    \"Beneficio_Esperado\": \"Beneficio 1; Beneficio 2\",\n" +
                "    \"Link_TradeStory\": \"https://unilever.sharepoint.com/sites/Brazil/Pages/As-Politicas-do-Codigo.aspx\",\n" +
                "    \"Link_Material\": \"https://unilever.sharepoint.com/sites/Brazil/Pages/As-Politicas-do-Codigo.aspx\",\n" +
                "    \"Link_Ficha\": \"https://unilever.sharepoint.com/sites/Brazil/Pages/As-Politicas-do-Codigo.aspx\",\n" +
                "    \"Regras_de_Ouro\": \"Regra1;Regra2\",\n" +
                "    \"Novos_SKUs\": \"SKU1 - DESC1;SKU2 - DESC2;SKU3 - DESC3\",\n" +
                "    \"SKUs_delistados\": \"SKU4 - DESC4;SKU5 - DESC5;SKU6 - DESC6\",\n" +
                "    \"Categoria\": \"Deodorants\",\n" +
                "    \"Commodity\": \"PC\",\n" +
                "    \"Marca\": \"Rexona\",\n" +
                "    \"Quatidade_SKUs\": \"1\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"Projeto_Inovacao\": \"SEDA SIM SHORE\",\n" +
                "    \"Canal\": \"TODOS\",\n" +
                "    \"Data_lancamento\": \"Março - 19\",\n" +
                "    \"Beneficio_Esperado\": \"Beneficio 1; Beneficio 2\",\n" +
                "    \"Link_TradeStory\": \"https://unilever.sharepoint.com/sites/Brazil/Pages/As-Politicas-do-Codigo.aspx\",\n" +
                "    \"Link_Material\": \"https://unilever.sharepoint.com/sites/Brazil/Pages/As-Politicas-do-Codigo.aspx\",\n" +
                "    \"Link_Ficha\": \"https://unilever.sharepoint.com/sites/Brazil/Pages/As-Politicas-do-Codigo.aspx\",\n" +
                "    \"Regras_de_Ouro\": \"Regra1;Regra2\",\n" +
                "    \"Novos_SKUs\": \"SKU1 - DESC1;SKU2 - DESC2;SKU3 - DESC3\",\n" +
                "    \"SKUs_delistados\": \"SKU4 - DESC4;SKU5 - DESC5;SKU6 - DESC6\",\n" +
                "    \"Categoria\": \"WASH & CARE\",\n" +
                "    \"Commodity\": \"PC\",\n" +
                "    \"Marca\": \"SEDA\",\n" +
                "    \"Quatidade_SKUs\": \"27\"\n" +
                "  }\n" +
                "]"
        val fromJson = mGson.fromJson(json, Array<InovacaoProjetoModel>::class.java)
        Assert.assertNotNull(fromJson)
    }

    @Ignore
    @Test
    fun parseInovacaoTechnicalText_test() {
        val json = "[\n" +
                "  {\n" +
                "    \"icon\": \"e913\",\n" +
                "    \"color\": \"9CA7AC\",\n" +
                "    \"text\": \"HOME CARE\",\n" +
                "    \"projects\": \"7\",\n" +
                "    \"icon2\": \"e91a\",\n" +
                "    \"entidades\": \"02_INOVACAO_LISTA\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"icon\": \"e90d\",\n" +
                "    \"color\": \"9CA7AC\",\n" +
                "    \"text\": \"PERSONAL CARE\",\n" +
                "    \"projects\": \"7\",\n" +
                "    \"icon2\": \"e91a\",\n" +
                "    \"entidades\": \"02_INOVACAO_LISTA\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"icon\": \"e912\",\n" +
                "    \"color\": \"9CA7AC\",\n" +
                "    \"text\": \"FOODS\",\n" +
                "    \"projects\": \"5\",\n" +
                "    \"icon2\": \"e91a\",\n" +
                "    \"entidades\": \"02_INOVACAO_LISTA\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"icon\": \"e914\",\n" +
                "    \"color\": \"9CA7AC\",\n" +
                "    \"text\": \"ICE CREAM\",\n" +
                "    \"projects\": \"1\",\n" +
                "    \"icon2\": \"e914\",\n" +
                "    \"entidades\": \"02_INOVACAO_LISTA\"\n" +
                "  }\n" +
                "]"

        val fromJson = mGson.fromJson(json, Array<InnovationCardVerticalModel.Item>::class.java)
        Assert.assertNotNull(fromJson)
    }

    @Ignore
    @Test
    fun parseAdicionarAgendaModel_test() {
        val json = "{\n" +
                "    \"text\": \"Me lembre de ligar pra Jesus amanhã\",\n" +
                "    \"sessionCode\": \"7b3b86d8-bcd6-4243-9e5f-a2ae5e9f0b64\",\n" +
                "    \"answers\": [\n" +
                "        {\n" +
                "            \"title\": \"ADICIONAR_AGENDA\",\n" +
                "            \"text\": \"Pode deixar! Irei gravar o seguinte compromisso.\\nAssunto - ligar pra Jesus \\nData - 11/01/2019\",\n" +
                "            \"technicalText\": null,\n" +
                "            \"audio\": null,\n" +
                "            \"options\": [\n" +
                "                {\n" +
                "                    \"title\": \"Sim\",\n" +
                "                    \"text\": \"09_AGENDA_LEMBRAR_ADICIONAR_SIM\",\n" +
                "                    \"id\": 30,\n" +
                "                    \"action\": true\n" +
                "                },\n" +
                "                {\n" +
                "                    \"title\": \"Não\",\n" +
                "                    \"text\": \"Entendi, você pode repetir a mensagem então, por favor?\",\n" +
                "                    \"id\": 32,\n" +
                "                    \"action\": false\n" +
                "                },\n" +
                "                {\n" +
                "                    \"title\": \"Cancelar\",\n" +
                "                    \"text\": \"Prontinho, cancelado. Como eu posso ajudar você?\",\n" +
                "                    \"id\": 33,\n" +
                "                    \"action\": false\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"context\": {\n" +
                "        \"previous_node\": \"2\",\n" +
                "        \"intents\": [\n" +
                "            {\n" +
                "                \"name\": \"06_AGENDA_LEMBRAR_ADICIONAR_TEXTO\",\n" +
                "                \"confidence\": 0.9620626\n" +
                "            }\n" +
                "        ],\n" +
                "        \"next_node\": null,\n" +
                "        \"entities\": [\n" +
                "            {\n" +
                "                \"name\": \"amanhã\",\n" +
                "                \"value\": \"0\",\n" +
                "                \"position\": null,\n" +
                "                \"type\": \"builtin.datetimeV2.date\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"me lembre de\",\n" +
                "                \"value\": \"0.966618955\",\n" +
                "                \"position\": null,\n" +
                "                \"type\": \"Adicionar Agenda\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}"
        val conversations = mGson.fromJson(json, Conversations::class.java)

        val answer : Answer = conversations.answers?.get(0) ?: Answer()

        val textJulia = StringUtils.trimToEmpty(answer.text)

        // ASSUNTO
        val lastIndexOf1 = StringUtils.lastIndexOf(StringUtils.upperCase(textJulia), "ASSUNTO")
        val substring = StringUtils.substring(textJulia, lastIndexOf1 + "ASSUNTO".length)

        val lastIndexOf2 = StringUtils.lastIndexOf(StringUtils.upperCase(substring), "DATA")
        var textAssunto = StringUtils.substring(substring, 0, lastIndexOf2)
        textAssunto = StringUtils.remove(textAssunto, "-")
        textAssunto = StringUtils.remove(textAssunto, "\n")
        textAssunto = StringUtils.trim(textAssunto)

        // DATA
        var textData = StringUtils.substringAfterLast(StringUtils.upperCase(textJulia), "DATA")
        textData = StringUtils.remove(textData, "-")
        textData = StringUtils.remove(textData, "\n")
        textData = StringUtils.trim(textData)

        val items = arrayListOf<AdicionarAgendaModel>()

        for (opt in answer.options ?: arrayListOf()) {
            val model = AdicionarAgendaModel()

            //model.title = StringUtils.trimToEmpty(opt.title)
            //model.textAction = StringUtils.trimToEmpty(opt.text)
            //model.action = opt.action ?: false
            //model.assunto = textAssunto
            //model.data = textData

            items.add(model)
        }

        Assert.assertTrue(items.isNotEmpty())
    }

    @Ignore
    @Test
    fun parseListOfMap_test() {
        val json = "[\n" +
                "  {\n" +
                "    \"codProduto\": \"67506502\",\n" +
                "    \"valorProduto\": \"535.04\",\n" +
                "    \"quantidade\": \"10\",\n" +
                "    \"motivo\": \"\",\n" +
                "    \"dataCriacao\": \"12/12/2018\",\n" +
                "    \"dataRequerida\": \"\",\n" +
                "    \"dataAgenda\": null,\n" +
                "    \"codPedido\": \"49363318\",\n" +
                "    \"statusPedido\": \"FACT\",\n" +
                "    \"customerName\": \"JAD ZOGHEIB E CIA LTDA\",\n" +
                "    \"materialName\": \"COMFORT AMAC INTENSE PURO RELAX 12X500ML\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"codProduto\": \"67194484\",\n" +
                "    \"valorProduto\": \"180.36\",\n" +
                "    \"quantidade\": \"3\",\n" +
                "    \"motivo\": \"\",\n" +
                "    \"dataCriacao\": \"12/12/2018\",\n" +
                "    \"dataRequerida\": \"\",\n" +
                "    \"dataAgenda\": null,\n" +
                "    \"codPedido\": \"49363318\",\n" +
                "    \"statusPedido\": \"FACT\",\n" +
                "    \"customerName\": \"JAD ZOGHEIB E CIA LTDA\",\n" +
                "    \"materialName\": \"VIM DT SAN PAST AD CITRUS 20X30G\"\n" +
                "  }\n" +
                "]"
        val list = JavaUtils.parseJsonToListOfMap(json)
        Assert.assertNotNull(list)
    }

    @Ignore
    @Test
    fun parseAgendaModel_test() {
        /*
        val json = "[\n" +
                "  {\n" +
                "    \"Id\": \"00T2D000003QkxPUAS\",\n" +
                "    \"Subject\": null,\n" +
                "    \"Status\": \"Not Started\",\n" +
                "    \"Priority\": \"Normal\",\n" +
                "    \"Description\": \"RNL\",\n" +
                "    \"OwnerId\": \"005E00000044p1sIAA\",\n" +
                "    \"RecordTypeId\": \"0122D0000000Ab8QAE\",\n" +
                "    \"ActivityDate\": \"28/01/2019\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"Id\": \"00T2D000003QkEnUAK\",\n" +
                "    \"Subject\": \"Optional(\"Júlia me lembre de enviar relatório para Pão de Açúcar na data de 01/02\") - Test 001\",\n" +
                "    \"Status\": \"Not Started\",\n" +
                "    \"Priority\": \"Normal\",\n" +
                "    \"Description\": \"Lembrete julia Test 001\",\n" +
                "    \"OwnerId\": \"005E00000044p1sIAA\",\n" +
                "    \"RecordTypeId\": \"0122D0000000Ab8QAE\",\n" +
                "    \"ActivityDate\": \"01/02/2019\"\n" +
                "  }\n" +
                "]"
                */
        val json = "[\n" +
                "  {\n" +
                "    \"Id\": \"00T2D000003U3aJUAS\",\n" +
                "    \"Subject\": \"Lembrete julia\",\n" +
                "    \"Status\": \"Not Started\",\n" +
                "    \"Priority\": \"Normal\",\n" +
                "    \"Description\": \"meeting with tom\",\n" +
                "    \"OwnerId\": \"005E00000044p1sIAA\",\n" +
                "    \"RecordTypeId\": \"0122D0000000Ab8QAE\",\n" +
                "    \"ActivityDate\": \"19/02/2019\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"Id\": \"00T2D000003TvJJUA0\",\n" +
                "    \"Subject\": \"Tarefas do mês\",\n" +
                "    \"Status\": \"Not Started\",\n" +
                "    \"Priority\": \"Normal\",\n" +
                "    \"Description\": \"VISITAR LOJAS PARA IDENTIFICAR OPORTUNIDADES DO MÊS ATUAL E SEGUINTE\",\n" +
                "    \"OwnerId\": \"005E00000044p1sIAA\",\n" +
                "    \"RecordTypeId\": \"0122D0000000Ab8QAE\",\n" +
                "    \"ActivityDate\": \"22/02/2019\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"Id\": \"00T2D000003TvJOUA0\",\n" +
                "    \"Subject\": \"Tarefas do mês\",\n" +
                "    \"Status\": \"Not Started\",\n" +
                "    \"Priority\": \"Normal\",\n" +
                "    \"Description\": \"ACOMPANHAR A CARTEIRA DE PEDIDOS PARA FATURAMENTO, CANCELAMENTOS, AGENDAS E ETC.\",\n" +
                "    \"OwnerId\": \"005E00000044p1sIAA\",\n" +
                "    \"RecordTypeId\": \"0122D0000000Ab8QAE\",\n" +
                "    \"ActivityDate\": \"22/02/2019\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"Id\": \"00T2D000003TvJTUA0\",\n" +
                "    \"Subject\": \"Tarefas do mês\",\n" +
                "    \"Status\": \"Not Started\",\n" +
                "    \"Priority\": \"Normal\",\n" +
                "    \"Description\": \"GARANTIR CADASTRO DAS INOVAÇÕES DO PRÓXIMO MÊS\",\n" +
                "    \"OwnerId\": \"005E00000044p1sIAA\",\n" +
                "    \"RecordTypeId\": \"0122D0000000Ab8QAE\",\n" +
                "    \"ActivityDate\": \"22/02/2019\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"Id\": \"00T2D000003TvJYUA0\",\n" +
                "    \"Subject\": \"Tarefas do mês\",\n" +
                "    \"Status\": \"Not Started\",\n" +
                "    \"Priority\": \"Normal\",\n" +
                "    \"Description\": \"AUXILIAR DE AGENDAS PARA RECEBIMENTO NOS CLIENTES JUNTAMENTE A GS\",\n" +
                "    \"OwnerId\": \"005E00000044p1sIAA\",\n" +
                "    \"RecordTypeId\": \"0122D0000000Ab8QAE\",\n" +
                "    \"ActivityDate\": \"22/02/2019\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"Id\": \"00T2D000003TvJ9UAK\",\n" +
                "    \"Subject\": \"Tarefas do mês\",\n" +
                "    \"Status\": \"Not Started\",\n" +
                "    \"Priority\": \"Normal\",\n" +
                "    \"Description\": \"ATUALIZAR CONTA CORRENTE E ENVIAR AO SEU COORDENADOR\",\n" +
                "    \"OwnerId\": \"005E00000044p1sIAA\",\n" +
                "    \"RecordTypeId\": \"0122D0000000Ab8QAE\",\n" +
                "    \"ActivityDate\": \"23/02/2019\"\n" +
                "  }\n" +
                "]"
        val array = AgendaModelParser.parserToObject(json)

        Assert.assertTrue(array.size == 2)
    }

    @Ignore
    @Test
    fun parseOption_test() {
        val json = "{\n" +
                "    \"text\": \"qual o processo de cadastro de clientes\",\n" +
                "    \"sessionCode\": \"c097907b-b54a-419c-a185-afacf4cf652a\",\n" +
                "    \"answers\": [\n" +
                "        {\n" +
                "            \"title\": \"02_SEXO\",\n" +
                "            \"text\": \"O Cadastro de Clientes deve ser realizado através do Portal do mSeries, veja um tutorial no vídeo abaixo.\",\n" +
                "            \"technicalText\": null,\n" +
                "            \"audio\": null,\n" +
                "            \"options\": [\n" +
                "                {\n" +
                "                    \"title\": \"Como cadastrar clientes no mSeries\",\n" +
                "                    \"text\": \"https://web.microsoftstream.com/video/e682c7f6-d81e-4da2-a818-5cbd601cf0ce\",\n" +
                "                    \"id\": 162,\n" +
                "                    \"action\": false\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"context\": {\n" +
                "        \"previous_node\": \"1072\",\n" +
                "        \"intents\": [\n" +
                "            {\n" +
                "                \"name\": \"39_CADASTRO_CLIENTE_PROCESSO\",\n" +
                "                \"confidence\": 0.8258838\n" +
                "            }\n" +
                "        ],\n" +
                "        \"next_node\": null,\n" +
                "        \"entities\": []\n" +
                "    }\n" +
                "}"
        val conversations = mGson.fromJson(json, Conversations::class.java)
        val url : String = conversations.getAnswer().options()[0].text ?: ""
        Assert.assertTrue(url.isNotEmpty())
    }
}