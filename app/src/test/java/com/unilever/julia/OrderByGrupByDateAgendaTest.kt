package com.unilever.julia

import com.google.gson.Gson
import com.unilever.julia.data.model.AgendaModel
import org.junit.Assert
import org.junit.Test
import java.util.*

class OrderByGrupByDateAgendaTest {

    @Test
    fun parseAgendaModel_test() {
        val json = "[\n" +
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

        val array = Gson().fromJson(json, Array<AgendaModel.Item>::class.java)

        array.sortBy({selector(it)})

        //array.forEach { println(it.data) }

        val groupByArray = array.groupBy { it.getData() }

        val list = arrayListOf<AgendaModel.Item>()
        for (map in groupByArray) {
            var count = 0
            for (it in map.value) {
                if (count == 0) {
                    it.dataVisible = true
                }
                it.dataDate = map.key
                list.add(it)
                count++
            }
        }

        //groupByArray.forEach { println(it) }

        list.forEach { println("data= ${it.activityDate}, visible= ${it.dataVisible}") }

        Assert.assertTrue(array.size == 5)
    }

    fun selector(p: AgendaModel.Item): Date? = p.getData()
}