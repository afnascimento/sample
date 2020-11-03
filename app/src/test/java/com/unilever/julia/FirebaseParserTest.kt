package com.unilever.julia

import com.google.gson.JsonParser
import com.unilever.julia.firebase.parser.ActionCodeNotification
import com.unilever.julia.firebase.parser.ActionNoneNotification
import com.unilever.julia.firebase.parser.ActionTextNotification
import com.unilever.julia.firebase.parser.FirebaseParser
import com.unilever.julia.utils.JavaUtils
import org.hamcrest.CoreMatchers.*
import org.junit.Assert
import org.junit.Test

class FirebaseParserTest {

    @Test
    fun parserActionTextNotification_test() {
        val json = "{\n" +
                "  \"title\": \"Lançamento Inovações 4\",\n" +
                "  \"body\": \"Temos um modelo novo de inovações esperando por você, clique e confira!\",\n" +
                "  \"action\": {\n" +
                "    \"type\": \"text\",\n" +
                "    \"screen\": null,\n" +
                "    \"value\": \"minhas tarefas\",\n" +
                "    \"context\": {\n" +
                "      \"id\": 4\n" +
                "    }\n" +
                "  }\n" +
                "}"
        val data = JavaUtils.parseJsonToMap(json)

        val notification = FirebaseParser().parser(data)
        Assert.assertNotNull(notification)
        Assert.assertTrue(notification is ActionTextNotification)

        val textNotification : ActionTextNotification = notification as ActionTextNotification
        Assert.assertThat(textNotification.mTextChat, `is`("minhas tarefas"))
    }

    @Test
    fun parserActionCodeNotification_test() {
        val json = "{\n" +
                "  \"title\": \"Lançamento Inovações 4\",\n" +
                "  \"body\": \"Temos um modelo novo de inovações esperando por você, clique e confira!\",\n" +
                "  \"action\": {\n" +
                "    \"type\": \"code\",\n" +
                "    \"screen\": null,\n" +
                "    \"value\": \"03_NOTIFICACOES_DETALHE\",\n" +
                "    \"context\": {\n" +
                "      \"id\": 4\n" +
                "    }\n" +
                "  }\n" +
                "}"
        val jsonObject = JsonParser().parse(json).asJsonObject

        val notification = FirebaseParser().parser(jsonObject)
        Assert.assertNotNull(notification)
        Assert.assertTrue(notification is ActionCodeNotification)

        val textNotification : ActionCodeNotification = notification as ActionCodeNotification
        Assert.assertThat(textNotification.mIntent, `is`("03_NOTIFICACOES_DETALHE"))
        Assert.assertThat(textNotification.mId, `is`("4"))
    }

    @Test
    fun parserActionCodeNotification_test2() {
        val data = linkedMapOf<String, String>()
        data["action"] = "{\"context\":{\"id\":\"5\"},\"screen\":\"\\/notificacao_detalhe\",\"type\":\"code\",\"value\":\"03_NOTIFICACOES_DETALHE\"}"
        data["title"] = "Devido a alguns probleminhas produtivos, a abertura de vendas de Sétima Geração será em 14/10/19."
        data["body"] = "Nova data Sétima Geração"

        val notification = FirebaseParser().parser(data)
        Assert.assertNotNull(notification)
        Assert.assertTrue(notification is ActionCodeNotification)

        val textNotification : ActionCodeNotification = notification as ActionCodeNotification
        Assert.assertThat(textNotification.mIntent, `is`("03_NOTIFICACOES_DETALHE"))
        Assert.assertThat(textNotification.mId, `is`("5"))
    }

    @Test
    fun parserActionNoneNotification_test1() {
        val json = "{\n" +
                "  \"title\": \"Title\",\n" +
                "  \"body\": \"Body\",\n" +
                "  \"action\": null\n" +
                "}"
        val jsonObject = JsonParser().parse(json).asJsonObject

        val notification = FirebaseParser().parser(jsonObject)
        Assert.assertNotNull(notification)
        Assert.assertTrue(notification is ActionNoneNotification)

        val textNotification : ActionNoneNotification = notification as ActionNoneNotification
        Assert.assertThat(textNotification.mTitle, `is`("Title"))
        Assert.assertThat(textNotification.mBody, `is`("Body"))
    }

    @Test
    fun parserActionNoneNotification_test2() {
        val json = "{\n" +
                "  \"title\": \"Title\",\n" +
                "  \"body\": \"Body\",\n" +
                "  \"action\": {\n" +
                "    \"type\": \"none\",\n" +
                "    \"screen\": null,\n" +
                "    \"value\": null,\n" +
                "    \"context\": null\n" +
                "  }\n" +
                "}"
        val jsonObject = JsonParser().parse(json).asJsonObject

        val notification = FirebaseParser().parser(jsonObject)
        Assert.assertNotNull(notification)
        Assert.assertTrue(notification is ActionNoneNotification)

        val textNotification : ActionNoneNotification = notification as ActionNoneNotification
        Assert.assertThat(textNotification.mTitle, `is`("Title"))
        Assert.assertThat(textNotification.mBody, `is`("Body"))
    }

    @Test
    fun parserActionNoneNotification_test3() {
        val json = "{\n" +
                "  \"title\": \"Title\",\n" +
                "  \"body\": \"Body\",\n" +
                "  \"action\": {\n" +
                "    \"type\": \"code\",\n" +
                "    \"screen\": null,\n" +
                "    \"value\": \"03_NOTIFICACOES_DETALHE\",\n" +
                "    \"context\": null\n" +
                "  }\n" +
                "}"
        val jsonObject = JsonParser().parse(json).asJsonObject

        val notification = FirebaseParser().parser(jsonObject)
        Assert.assertNotNull(notification)
        Assert.assertTrue(notification is ActionNoneNotification)

        val textNotification : ActionNoneNotification = notification as ActionNoneNotification
        Assert.assertThat(textNotification.mTitle, `is`("Title"))
        Assert.assertThat(textNotification.mBody, `is`("Body"))
    }
}