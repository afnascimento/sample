package com.unilever.julia.data.model.parser

import android.content.Context
import com.google.gson.Gson
import com.unilever.julia.components.model.IComponentsModel
import com.unilever.julia.data.model.Conversations
import com.unilever.julia.components.model.BoletimCard
import com.unilever.julia.components.model.BoletimChatModel
import com.unilever.julia.components.model.BoletimItem
import com.unilever.julia.data.model.bulletin.ResultInformation
import com.unilever.julia.data.model.bulletin.Sale
import com.unilever.julia.data.model.bulletin.SalesBulletinData
import com.unilever.julia.data.model.bulletin.UpdateInformations
import com.unilever.julia.utils.NumberUtils
import org.apache.commons.lang3.StringUtils

class ParserBoletimChat(context: Context, gson : Gson, conversations: Conversations) : IParserModel {

    private var model = BoletimChatModel()

    private val JSON = "{\n" +
            "  \"salesBulletin\": {\n" +
            "    \"territory\": \"031068\",\n" +
            "    \"sales\": [\n" +
            "      {\n" +
            "        \"title\": \"NIV\",\n" +
            "        \"estimated_value\": 13437979.1,\n" +
            "        \"percentage\": 31.93,\n" +
            "        \"description\": \"Estimativa\",\n" +
            "        \"update_informations\": {\n" +
            "          \"title\": \"Entrada/dia\",\n" +
            "          \"updated\": \"Atualizado hoje às 15:12\",\n" +
            "          \"value\": 7000.00\n" +
            "        },\n" +
            "        \"result_informations\": [\n" +
            "          {\n" +
            "            \"title\": \"Entrada\",\n" +
            "            \"result_description\": \"Resultado do dia 13/10\",\n" +
            "            \"value\": 14000000.00,\n" +
            "            \"percentage\": 65.00\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      {\n" +
            "        \"title\": \"TON\",\n" +
            "        \"estimated_value\": 13437979.1,\n" +
            "        \"percentage\": 60.93,\n" +
            "        \"description\": \"Estimativa\",\n" +
            "        \"update_informations\": {\n" +
            "          \"title\": \"Entrada/dia\",\n" +
            "          \"updated\": \"Atualizado hoje às 15:12\",\n" +
            "          \"value\": 7000.00\n" +
            "        },\n" +
            "        \"result_informations\": [\n" +
            "          {\n" +
            "            \"title\": \"Entrada\",\n" +
            "            \"result_description\": \"Resultado do dia 13/10\",\n" +
            "            \"value\": 14000000.00,\n" +
            "            \"percentage\": 90.00\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}"

    init {
        model.text = conversations.text ?: ""
        try {
            val bulletin = gson.fromJson(
                conversations.getAnswer().technicalText, //JSON
                SalesBulletinData::class.java
            )

            val salesBulletin = bulletin.salesBulletin
            if (salesBulletin == null) {
                model.text = "Boletim de vendas não encontrado!"
            } else {
                if (StringUtils.isEmpty(salesBulletin.territory)) {
                    model.text = "Território não encontrado!"
                } else if (!salesBulletin.sales.isNullOrEmpty() && salesBulletin.sales?.size == 2) {
                    val card = BoletimCard()
                    card.territory = salesBulletin.territory ?: ""
                    card.nivel = getBoletimItem(salesBulletin.sales?.get(0))
                    card.tonelada = getBoletimItem(salesBulletin.sales?.get(1))
                    model.card = card
                } else {
                    model.text = "Dados do nível, toneladas não encontrados!"
                }
            }
        } catch (e : Throwable) {
            e.printStackTrace()
        }
    }

    private fun getBoletimItem(sale: Sale?): BoletimItem {
        val item = BoletimItem()
        item.header = getBoletimItemHeader(sale)
        item.entradaDia = getEntradaDia(sale)
        item.entrada = getBoletimItemPercentage(sale, "Entrada")
        item.faturado = getBoletimItemPercentage(sale, "Faturado")
        return item
        //item.entradaDia?.color = JavaUtils.toHexString(context, R.color.colorBlue)
        //item.entrada?.color = JavaUtils.toHexString(context, R.color.colorOrange)
    }

    private fun getBoletimItemPercentage(sale : Sale?, title: String): BoletimItem.Percentage? {
        val filter = sale?.resultInformations?.filter { StringUtils.equalsIgnoreCase(it.title, title) }

        if (filter.isNullOrEmpty()) {
            return null
        }

        val resultInformation : ResultInformation = filter.get(0)

        val result = BoletimItem.Percentage()
        result.title = resultInformation.title ?: ""
        result.description = resultInformation.resultDescription ?: ""
        result.value = resultInformation.value ?: 0.0
        result.percentage = resultInformation.percentage ?: 0.0
        result.color = resultInformation.color ?: ""
        return result
    }

    private fun getEntradaDia(sale : Sale?): BoletimItem.Percentage? {
        val updateInformations : UpdateInformations = sale?.updateInformations ?: return null

        val result = BoletimItem.Percentage()
        result.title = updateInformations.title ?: ""
        result.description = updateInformations.updated ?: ""

        val valEstimate = updateInformations.value ?: 0.0
        result.value = valEstimate

        val valTotal : Double = sale.estimatedValue ?: 0.0
        //(valEstimate * 100) / valTotal
        val valPercentage = NumberUtils.roundDecimal((valEstimate * 100) / valTotal, 2)
        result.percentage = valPercentage

        result.color = updateInformations.color ?: ""
        return result
    }

    private fun getBoletimItemHeader(sale : Sale?) : BoletimItem.Header {
        val header = BoletimItem.Header()
        header.title = sale?.title ?: ""
        header.description = sale?.description ?: ""
        header.value = sale?.estimatedValue ?: 0.0
        header.percentage = sale?.percentage ?: 0.0
        return header
    }

    override fun getText(): String {
        return model.text
    }

    override fun getModel(): IComponentsModel {
        return model
    }
}