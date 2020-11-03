package com.unilever.julia.ui.inovacao.detalhe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.unilever.julia.R
import com.unilever.julia.data.model.InovacaoProjetoModel
import com.unilever.julia.ui.base.BaseViewActivity
import com.unilever.julia.components.ButtonWrapContent
import com.unilever.julia.ui.component.InovacaoListItemsComponent
import com.unilever.julia.utils.RedirectIntents
import com.unilever.julia.utils.Utils
import kotlinx.android.synthetic.main.inovacao_detalhe_activity.*
import kotlinx.android.synthetic.main.inovacao_detalhe_beneficio.*
import kotlinx.android.synthetic.main.toolbar_back.*
import kotlinx.android.synthetic.main.toolbar_back_content.*
import org.apache.commons.lang3.StringUtils
import javax.inject.Inject

class InovacaoDetalheActivity : BaseViewActivity(), InovacaoDetalheView {

    @Inject
    lateinit var mPresenter : InovacaoDetalhePresenter<InovacaoDetalheView, InovacaoDetalheInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inovacao_detalhe_activity)
        setSupportActionBar(toolbarBack)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = ""

        toolbarBackArrow.setOnClickListener {
            onBackPressed()
        }

        val inovacaoDetalhe = intent.getParcelableExtra<InovacaoProjetoModel>(RedirectIntents.EXTRA_INOVACAO_DETALHE)

        inovacaoDetProjetoCard.setItem(inovacaoDetalhe)
        inovacaoDetProjetoCard.setVisibleButton(false)

        // beneficios
        contentBeneficiosItems.removeAllViews()
        for (text in StringUtils.split(StringUtils.trimToEmpty(inovacaoDetalhe.beneficioEsperado), ";")) {
            val tvBeneficioItem : TextView = LayoutInflater.from(this).inflate(R.layout.inovacao_beneficio_item_textview, null, false) as TextView
            tvBeneficioItem.text = StringUtils.trimToEmpty(text)
            contentBeneficiosItems.addView(tvBeneficioItem)
        }

        // Regra de ouro
        val regraOuroSplit = StringUtils.split(StringUtils.trimToEmpty(inovacaoDetalhe.regrasDeOuro), ";").toMutableList()

        val regraOuroComponent = InovacaoListItemsComponent(this)
        regraOuroComponent.setTitle(getString(R.string.regras_ouro))
        regraOuroComponent.setItems(regraOuroSplit)
        contentDetalheMain.addView(regraOuroComponent)

        // Publico Alvo
        val publicoAlvoSplit = StringUtils.split(StringUtils.trimToEmpty(inovacaoDetalhe.publicoAlvo), ";").toMutableList()

        val publicoAlvoComponent = InovacaoListItemsComponent(this)
        publicoAlvoComponent.setTitle(getString(R.string.publico_alvo))
        publicoAlvoComponent.setItems(publicoAlvoSplit)
        contentDetalheMain.addView(publicoAlvoComponent)

        // button tradestory
        if (StringUtils.isNotEmpty(inovacaoDetalhe.linkTradeStory)) {
            val url = inovacaoDetalhe.linkTradeStory ?: ""
            val button = getButton(getString(R.string.tradestory), url, View.OnClickListener {
                mPresenter.sendEventTradestory(inovacaoDetalhe)
                openLink(url)
            })
            contentDetalheMain.addView(button)
        }

        // button material visibilidade
        if (StringUtils.isNotEmpty(inovacaoDetalhe.linkMaterial)) {
            val button = getButton(getString(R.string.material_visibilidade), inovacaoDetalhe.linkMaterial ?: "", null)
            contentDetalheMain.addView(button)
        }

        // button ficha cadastral
        if (StringUtils.isNotEmpty(inovacaoDetalhe.linkFicha)) {
            val button = getButton(getString(R.string.ficha_cadastral), inovacaoDetalhe.linkFicha ?: "", null)
            contentDetalheMain.addView(button)
        }

        // Novos Skus
        val novosSkusSplit = StringUtils.split(StringUtils.trimToEmpty(inovacaoDetalhe.novosSkus), ";").toMutableList()

        val novosSkusComponent = InovacaoListItemsComponent(this)
        novosSkusComponent.setTitle(getString(R.string.novos_skus))
        novosSkusComponent.setItems(novosSkusSplit)
        contentDetalheMain.addView(novosSkusComponent)

        // Skus Deslistados
        val skusDeslistadosSplit = StringUtils.split(StringUtils.trimToEmpty(inovacaoDetalhe.skusDeListados), ";").toMutableList()

        val skusDeslistadosComponent = InovacaoListItemsComponent(this)
        skusDeslistadosComponent.setTitle(getString(R.string.skus_deslistados))
        skusDeslistadosComponent.setItems(skusDeslistadosSplit)
        contentDetalheMain.addView(skusDeslistadosComponent)

        mPresenter.sendEventInnovation(inovacaoDetalhe)
    }

    private fun getButton(text: String, url: String, onClickListener: View.OnClickListener?): View {
        val view = LayoutInflater.from(this).inflate(R.layout.inovacao_detalhe_button_content, null, false)

        val button = view.findViewById<ButtonWrapContent>(R.id.button)
        button.setText(text)
        button.setColor(ButtonWrapContent.ButtonColor.GRAY)

        if (onClickListener == null) {
            button.setOnClickListener { openLink(url) }
        } else {
            button.setOnClickListener(onClickListener)
        }

        return view
    }

    private fun openLink(url: String) {
        Utils.openLinkInBrowser(this@InovacaoDetalheActivity, url)
    }
}