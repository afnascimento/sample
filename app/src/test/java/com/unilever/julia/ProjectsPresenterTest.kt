package com.unilever.julia

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.unilever.julia.data.model.InnovationProjectsModel
import com.unilever.julia.ui.inovacao.projectsV2.ProjectsInteractor
import com.unilever.julia.ui.inovacao.projectsV2.ProjectsPresenter
import com.unilever.julia.ui.inovacao.projectsV2.ProjectsPresenterImpl
import com.unilever.julia.ui.inovacao.projectsV2.ProjectsView
import com.unilever.julia.utils.ParsePatternsEnums
import com.unilever.julia.utils.parseDate
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class ProjectsPresenterTest {

    lateinit var mGson: Gson

    @Mock
    lateinit var mView : ProjectsView

    @Mock
    lateinit var mInteractor : ProjectsInteractor

    lateinit var mPresenter : ProjectsPresenter<ProjectsView, ProjectsInteractor>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mPresenter = ProjectsPresenterImpl(mView, mInteractor)

        mGson = GsonBuilder()
            .registerTypeAdapter(
                InnovationProjectsModel.Item::class.java,
                InnovationProjectsModel.Deserializer("", ""))
            .serializeNulls()
            .setPrettyPrinting()
            .create()
    }

    @Test
    fun sortedProjectsByDate_test() {
        // cenário
        val json : String = "{\n" +
                "    \"projetos\": [\n" +
                "        {\n" +
                "            \"name\": \"HISTÓRICO\",\n" +
                "            \"itens\": [\n" +
                "                {\n" +
                "                    \"title\": \"BEN & JERRY'S\",\n" +
                "                    \"image\": \"https://juliajobfunction.blob.core.windows.net/innovations/Marcas/Unilever_Logo_200x200_BeJ.jpg\",\n" +
                "                    \"data\": \"2019-04-01 00:00:00.0\",\n" +
                "                    \"canal\": \"TODOS\",\n" +
                "                    \"codeProduto\": \"EAN76840001651; EAN76840001927; EAN76840002122 ;EAN76840560707; EAN76840721795; EAN76840376292\",\n" +
                "                    \"identifier\": \"20191001_benandjerrys_02\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"title\": \"KIBON\",\n" +
                "                    \"image\": \"https://juliajobfunction.blob.core.windows.net/innovations/Marcas/Unilever_Logo_200x200_Kibon.jpg\",\n" +
                "                    \"data\": \"2019-07-01 00:00:00.0\",\n" +
                "                    \"canal\": \"TODOS\",\n" +
                "                    \"codeProduto\": \"EAN7891150050235; EAN7891150050235; EAN7891150050242; EAN7891150055858; EAN7891150026742; EAN7891150039384; EAN7891150055858; EAN7891150026773; EAN7891150050242; EAN7891150050235_NE; EAN7891150050242_NE; EAN7891150055858_NE;undefined\",\n" +
                "                    \"identifier\": \"20191001_kibon_02\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"EM ABERTO\",\n" +
                "            \"itens\": [\n" +
                "                {\n" +
                "                    \"title\": \"BEN & JERRY'S\",\n" +
                "                    \"image\": \"https://juliajobfunction.blob.core.windows.net/innovations/Marcas/Unilever_Logo_200x200_BeJ.jpg\",\n" +
                "                    \"data\": \"2019-09-01 00:00:00.0\",\n" +
                "                    \"canal\": \"DIRETA\",\n" +
                "                    \"codeProduto\": \"EAN076840002139;EAN76840000531\",\n" +
                "                    \"identifier\": \"20191001_benandjerrys_01\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"title\": \"KIBON\",\n" +
                "                    \"image\": \"https://juliajobfunction.blob.core.windows.net/innovations/Marcas/Unilever_Logo_200x200_Kibon.jpg\",\n" +
                "                    \"data\": \"2019-09-01 00:00:00.0\",\n" +
                "                    \"canal\": \"TODOS\",\n" +
                "                    \"codeProduto\": \"EAN7891150066342;undefined\",\n" +
                "                    \"identifier\": \"20191001_kibon_05\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"title\": \"KIBON\",\n" +
                "                    \"image\": \"https://juliajobfunction.blob.core.windows.net/innovations/Marcas/Unilever_Logo_200x200_Kibon.jpg\",\n" +
                "                    \"data\": \"2019-09-01 00:00:00.0\",\n" +
                "                    \"canal\": \"TODOS\",\n" +
                "                    \"codeProduto\": \"EAN7891150064904;EAN7891150047341\",\n" +
                "                    \"identifier\": \"20191001_kibon_04\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"title\": \"KIBON\",\n" +
                "                    \"image\": \"https://juliajobfunction.blob.core.windows.net/innovations/Marcas/Unilever_Logo_200x200_Kibon.jpg\",\n" +
                "                    \"data\": \"2019-09-01 00:00:00.0\",\n" +
                "                    \"canal\": \"TODOS\",\n" +
                "                    \"codeProduto\": \"EAN7891150067394;undefined\",\n" +
                "                    \"identifier\": \"20191001_kibon_08\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"title\": \"KIBON\",\n" +
                "                    \"image\": \"https://juliajobfunction.blob.core.windows.net/innovations/Marcas/Unilever_Logo_200x200_Kibon.jpg\",\n" +
                "                    \"data\": \"2019-09-01 00:00:00.0\",\n" +
                "                    \"canal\": \"TODOS\",\n" +
                "                    \"codeProduto\": \"EAN7891150066175;undefined\",\n" +
                "                    \"identifier\": \"20191001_kibon_06\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"title\": \"KIBON\",\n" +
                "                    \"image\": \"https://juliajobfunction.blob.core.windows.net/innovations/Marcas/Unilever_Logo_200x200_Kibon.jpg\",\n" +
                "                    \"data\": \"2019-09-01 00:00:00.0\",\n" +
                "                    \"canal\": \"TODOS\",\n" +
                "                    \"codeProduto\": \"EAN7891150065925;EAN7891150051959\",\n" +
                "                    \"identifier\": \"20191001_kibon_07\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"title\": \"KIBON\",\n" +
                "                    \"image\": \"https://juliajobfunction.blob.core.windows.net/innovations/Marcas/Unilever_Logo_200x200_Kibon.jpg\",\n" +
                "                    \"data\": \"2019-09-01 00:00:00.0\",\n" +
                "                    \"canal\": \"TODOS\",\n" +
                "                    \"codeProduto\": \"EAN7891150066366 ;EAN7891150055032\",\n" +
                "                    \"identifier\": \"20191001_kibon_09\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"title\": \"KIBON\",\n" +
                "                    \"image\": \"https://juliajobfunction.blob.core.windows.net/innovations/Marcas/Unilever_Logo_200x200_Kibon.jpg\",\n" +
                "                    \"data\": \"2019-09-01 00:00:00.0\",\n" +
                "                    \"canal\": \"TODOS\",\n" +
                "                    \"codeProduto\": \"EAN7891150066229;undefined\",\n" +
                "                    \"identifier\": \"20191001_kibon_10\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"AGUARDANDO\",\n" +
                "            \"itens\": [\n" +
                "                {\n" +
                "                    \"title\": \"KIBON\",\n" +
                "                    \"image\": \"https://juliajobfunction.blob.core.windows.net/innovations/Marcas/Unilever_Logo_200x200_Kibon.jpg\",\n" +
                "                    \"data\": \"2019-11-01 00:00:00.0\",\n" +
                "                    \"canal\": \"MV+SAM'S+PÃO DE AÇUCAR. SOMENTE LESTE, SP E SUL\",\n" +
                "                    \"codeProduto\": \"EAN7891150067585;undefined\",\n" +
                "                    \"identifier\": \"20191001_kibon_01\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"title\": \"KIBON2\",\n" +
                "                    \"image\": \"https://juliajobfunction.blob.core.windows.net/innovations/Marcas/Unilever_Logo_200x200_Kibon.jpg\",\n" +
                "                    \"data\": \"2019-12-31 00:00:00.0\",\n" +
                "                    \"canal\": \"MV+SAM'S+PÃO DE AÇUCAR. SOMENTE LESTE, SP E SUL\",\n" +
                "                    \"codeProduto\": \"EAN7891150067585;undefined\",\n" +
                "                    \"identifier\": \"20191001_kibon_01\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"title\": \"KIBON\",\n" +
                "                    \"image\": \"https://juliajobfunction.blob.core.windows.net/innovations/Marcas/Unilever_Logo_200x200_Kibon.jpg\",\n" +
                "                    \"data\": \"2019-12-01 00:00:00.0\",\n" +
                "                    \"canal\": \"TODOS\",\n" +
                "                    \"codeProduto\": \"EAN7891150067912;undefined\",\n" +
                "                    \"identifier\": \"20191001_kibon_03\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}"
        val model = mGson.fromJson(json, InnovationProjectsModel::class.java)

        // ação
        val projects = mPresenter.sortedProjectsByDate(model.projects ?: arrayListOf())

        // verificação
        Assert.assertThat(projects.size, `is`(3))
        val aba_aguardando = projects.get(2)
        Assert.assertThat(aba_aguardando.name, `is`("AGUARDANDO"))

        val item1_novembro : InnovationProjectsModel.Item = aba_aguardando.items?.get(0) ?: InnovationProjectsModel.Item()
        Assert.assertThat(item1_novembro.dataParse, `is`("01/11/2019".parseDate(ParsePatternsEnums.DDMMYYYY)))
        Assert.assertThat(item1_novembro.isTitle, `is`(true))

        val item2_dezembro : InnovationProjectsModel.Item = aba_aguardando.items?.get(1) ?: InnovationProjectsModel.Item()
        Assert.assertThat(item2_dezembro.dataParse, `is`("01/12/2019".parseDate(ParsePatternsEnums.DDMMYYYY)))
        Assert.assertThat(item2_dezembro.isTitle, `is`(true))

        val item3_dezembro : InnovationProjectsModel.Item = aba_aguardando.items?.get(2) ?: InnovationProjectsModel.Item()
        Assert.assertThat(item3_dezembro.dataParse, `is`("31/12/2019".parseDate(ParsePatternsEnums.DDMMYYYY)))
        Assert.assertThat(item3_dezembro.isTitle, `is`(false))



        /*
        val model = InnovationProjectsModel()
            .addProject(
                InnovationProjectsModel.Project()
                    .setName("Project1")
                    .addItem(InnovationProjectsModel.Item().setTitle("Product1").setData("01/09/2019"))
                    .addItem(InnovationProjectsModel.Item().setTitle("Product2").setData("01/07/2019"))
                    .addItem(InnovationProjectsModel.Item().setTitle("Product3").setData("01/06/2019"))
                    .addItem(InnovationProjectsModel.Item().setTitle("Product4").setData("05/06/2019"))
            )
            .addProject(
                InnovationProjectsModel.Project()
                    .setName("Project2")
            )

        val projects = mPresenter.sortedProjectsByDate(model.projects ?: arrayListOf())
        val project = projects[0]
        Assert.assertTrue(project.items!![0].getDataText() == "01/06/2019")
        Assert.assertTrue(project.items!![1].getDataText() == "05/06/2019")
        Assert.assertTrue(project.items!![2].getDataText() == "01/07/2019")
        Assert.assertTrue(project.items!![3].getDataText() == "01/09/2019")
         */
    }
}