package com.unilever.julia.ui.boletim.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.unilever.julia.R
import com.unilever.julia.components.LoadView
import com.unilever.julia.components.TagsModel
import com.unilever.julia.components.boletim.AttendanceModel
import com.unilever.julia.components.model.BoletimChatModel
import com.unilever.julia.components.boletim.BoletimFiltros
import com.unilever.julia.data.model.bulletin.BulletinsMultipleFiltersResponse
import com.unilever.julia.components.boletim.AttendanceFilter
import javax.inject.Inject
import com.unilever.julia.ui.base.*
import com.unilever.julia.utils.RedirectIntents
import kotlinx.android.synthetic.main.boletim_main_activity.*
import kotlinx.android.synthetic.main.toolbar_back.*
import kotlinx.android.synthetic.main.toolbar_back_content.*

class BoletimMainActivity : BaseViewActivity(), BoletimMainView {

    @Inject
    lateinit var mPresenter: BoletimMainPresenter<BoletimMainView, BoletimMainInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.boletim_main_activity)

        setSupportActionBar(toolbarBack)

        toolbarBackArrow.setOnClickListener {
            onBackPressed()
        }

        tvToolbarTitle.text = getString(R.string.filtros)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = ""

        bolAreaAtendimento.setOnClickListener {
            mPresenter.navegarAreaAtendimento()
        }

        bolCanal.setOnClickListener {
            mPresenter.navegarCanais()
        }

        bolClientes.setOnClickListener {
            mPresenter.navegarClientes()
        }

        bolCommodites.setOnClickListener {
            mPresenter.navegarCommodities()
        }

        bolCategorias.setOnClickListener {
            mPresenter.navegarCategorias()
        }

        bolMarcas.setOnClickListener {
            mPresenter.navegarMarcas()
        }

        btnAplicar.setOnClickListener {
            val filters = BoletimFiltros()
            filters.attendance = bolAreaAtendimento.getTags()
            filters.brand = bolMarcas.getTags()
            filters.category = bolCategorias.getTags()
            filters.commodity = bolCommodites.getTags()
            filters.customer = bolClientes.getTags()
            filters.channel = bolCanal.getTags()
            mPresenter.applyFilter(filters)
        }

        val territory : String = intent.getStringExtra(RedirectIntents.EXTRA_TERRITORY)
        val attendanceFilter: AttendanceFilter? = intent.getParcelableExtra(RedirectIntents.EXTRA_ATTENDANCE_FILTER)
        val filters: BoletimFiltros? = intent.getParcelableExtra(RedirectIntents.EXTRA_BOLETIM_FILTROS)

        mPresenter.init(territory, attendanceFilter, filters)
    }

    fun getPresenter(): BoletimMainPresenter<BoletimMainView, BoletimMainInteractor> {
        return mPresenter
    }

    override fun getLoadView(): LoadView? {
        return loadView
    }

    override fun getLoadContent(): View? {
        return contentMain
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    companion object {
        private const val CODE_AREA = 0
        private const val CODE_CANAIS = 1
        private const val CODE_CLIENTES = 2
        private const val CODE_COMMODITIES = 3
        private const val CODE_CATEGORIAS = 4
        private const val CODE_MARCAS = 5
    }

    override fun redirectBoletimMultipleActivity(type : BulletinsMultipleFiltersResponse.Type,
                                                 toolbarText : String,
                                                 hintText: String,
                                                 attendanceFilter: AttendanceFilter
    ) {
        val code : Int
        when (type) {
            BulletinsMultipleFiltersResponse.Type.CANAIS -> {
                code = CODE_CANAIS
            }
            BulletinsMultipleFiltersResponse.Type.CLIENTES -> {
                code = CODE_CLIENTES
            }
            BulletinsMultipleFiltersResponse.Type.COMMODITIES -> {
                code = CODE_COMMODITIES
            }
            BulletinsMultipleFiltersResponse.Type.CATEGORIAS -> {
                code = CODE_CATEGORIAS
            }
            BulletinsMultipleFiltersResponse.Type.MARCAS -> {
                code = CODE_MARCAS
            }
        }
        RedirectIntents.redirectBoletimMultipleActivity(this, code, type, toolbarText, hintText, attendanceFilter)
    }

    override fun redirectAttendanceActivity(myTerritory: String) {
        RedirectIntents.redirectAttendanceActivity(this, myTerritory, CODE_AREA)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == CODE_AREA) {
                val attendance = data.getParcelableExtra<AttendanceModel>(RedirectIntents.EXTRA_ATTENDANCE)
                mPresenter.updateAreaAtendimento(attendance)
            } else {
                val arrayList = data.getStringArrayListExtra(RedirectIntents.EXTRA_ARRAY_STRING)

                val tags : MutableList<TagsModel> = arrayListOf()

                for (it in arrayList) {
                    tags.add(TagsModel(it, it))
                }

                when (requestCode) {
                    CODE_CANAIS -> {
                        bolCanal.addTags(tags)
                    }
                    CODE_CLIENTES -> {
                        bolClientes.addTags(tags)
                    }
                    CODE_COMMODITIES -> {
                        bolCommodites.addTags(tags)
                    }
                    CODE_CATEGORIAS -> {
                        bolCategorias.addTags(tags)
                    }
                    CODE_MARCAS -> {
                        bolMarcas.addTags(tags)
                    }
                }
            }
        }
    }

    override fun onReturnWithNewData(model: BoletimChatModel, attendanceFilter: AttendanceFilter, filters: BoletimFiltros) {
        val intent = Intent()
        intent.putExtra(RedirectIntents.EXTRA_BOLETIMCHATMODEL, model)
        intent.putExtra(RedirectIntents.EXTRA_BOLETIM_FILTROS, filters)
        intent.putExtra(RedirectIntents.EXTRA_ATTENDANCE_FILTER, attendanceFilter)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun addTags(filters: BoletimFiltros) {
        bolAreaAtendimento.addTags(filters.attendance)
        bolCanal.addTags(filters.channel)
        bolClientes.addTags(filters.customer)
        bolCommodites.addTags(filters.commodity)
        bolCategorias.addTags(filters.category)
        bolMarcas.addTags(filters.brand)
    }

    override fun addAttendanceTag(tag : TagsModel) {
        bolAreaAtendimento.addUniqueTag(tag)
    }

    override fun clearAttendanceTag() {
        bolAreaAtendimento.clear()
    }
}