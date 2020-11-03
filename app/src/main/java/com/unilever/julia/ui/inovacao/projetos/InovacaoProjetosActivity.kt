package com.unilever.julia.ui.inovacao.projetos

import android.os.Bundle
import android.view.View
import com.unilever.julia.R
import com.unilever.julia.data.model.InovacaoProjetoModel
import com.unilever.julia.ui.base.BaseViewActivity
import com.unilever.julia.ui.component.JuliaInovacaoProjetoCard
import com.unilever.julia.components.LoadView
import com.unilever.julia.utils.RedirectIntents
import kotlinx.android.synthetic.main.inovacao_projetos_activity.*
import kotlinx.android.synthetic.main.inovacao_projetos_items.*
import kotlinx.android.synthetic.main.toolbar_back.*
import kotlinx.android.synthetic.main.toolbar_back_content.*
import javax.inject.Inject

class InovacaoProjetosActivity : BaseViewActivity(), InovacaoProjetosView {

    @Inject
    lateinit var mPresenter : InovacaoProjetosPresenter<InovacaoProjetosView, InovacaoProjetosInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inovacao_projetos_activity)

        setSupportActionBar(toolbarBack)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = ""

        toolbarBackArrow.setOnClickListener {
            onBackPressed()
        }

        val sessionCode = intent.getStringExtra(RedirectIntents.EXTRA_SESSION_CODE)
        val code = intent.getStringExtra(RedirectIntents.EXTRA_CODE)
        val marcaId = intent.getStringExtra(RedirectIntents.EXTRA_MARCA_ID)
        val categoriaId = intent.getStringExtra(RedirectIntents.EXTRA_CATEGORIA_ID)
        val commodity = intent.getStringExtra(RedirectIntents.EXTRA_COMMODITY)

        mPresenter.getProjetosInovacoes(sessionCode, code, marcaId, categoriaId, commodity)
    }

    override fun getLoadContent(): View? {
        return loadContent
    }

    override fun getLoadView(): LoadView? {
        return juliaLoadView
    }

    override fun addInovacoes(inovacoes: MutableList<InovacaoProjetoModel>) {
        for (it in inovacoes) {
            val comp = JuliaInovacaoProjetoCard(this)
            comp.setItem(it)
            comp.setAbreviateTextProject()
            comp.setListener(object : JuliaInovacaoProjetoCard.Listener {
                override fun onButtonDetailClick(item: InovacaoProjetoModel) {
                    RedirectIntents.redirectInovacaoDetalheActivity(this@InovacaoProjetosActivity, item)
                }
            })
            contentProjetosItems.addView(comp)
        }
    }
}