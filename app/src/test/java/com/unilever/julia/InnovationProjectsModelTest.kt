package com.unilever.julia

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.unilever.julia.data.model.InnovationProjectsModel
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test

class InnovationProjectsModelTest {

    companion object {
        @JvmStatic
        private lateinit var mGson: Gson

        @BeforeClass
        @JvmStatic
        fun initialize() {
            mGson = GsonBuilder()
                .registerTypeAdapter(InnovationProjectsModel.Item::class.java, InnovationProjectsModel.Deserializer("", ""))
                .serializeNulls()
                .setPrettyPrinting()
                .create()
        }
    }

    @Test
    fun parseJson_test1() {
        val obj = InnovationProjectsModel()
            .addProject(
                InnovationProjectsModel.Project()
                    .setName("TAB1")
                    .addItem(InnovationProjectsModel.Item().setTitle("Product1").setData("01/09/2019"))
                    .addItem(InnovationProjectsModel.Item().setTitle("Product2").setData("01/07/2019"))
                    .addItem(InnovationProjectsModel.Item().setTitle("Product3").setData("01/06/2019"))
                    .addItem(InnovationProjectsModel.Item().setTitle("Product4").setData("05/06/2019"))
            )

        val json = mGson.toJson(obj)

        val model = mGson.fromJson(json, InnovationProjectsModel::class.java)

        val dataText = model.projects?.get(0)?.items?.get(0)?.getDataText()
        Assert.assertEquals(dataText, "01/08/2019", dataText)
    }
}