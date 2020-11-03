package com.unilever.julia.ui.menu.configuracao

import android.os.Bundle
import com.unilever.julia.R
import com.unilever.julia.ui.base.BaseViewActivity
import kotlinx.android.synthetic.main.configuracao_activity.*
import kotlinx.android.synthetic.main.toolbar_back.*
import kotlinx.android.synthetic.main.toolbar_back_content.*
import javax.inject.Inject

class ConfiguracaoActivity : BaseViewActivity(), ConfiguracaoView {

    @Inject
    lateinit var mPresenter: ConfiguracaoPresenter<ConfiguracaoView, ConfiguracaoInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.configuracao_activity)
        setSupportActionBar(toolbarBack)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = ""

        tvToolbarTitle.text = getString(R.string.configuracoes)

        toolbarBackArrow.setOnClickListener{ onBackPressed() }

        switchVoz.setOnCheckedChangeListener { _, isChecked -> mPresenter.saveTextToSpeech(isChecked) }

        switchOutlook.setOnCheckedChangeListener { _, isChecked -> mPresenter.saveOutlook(isChecked) }

        switchIPV.setOnCheckedChangeListener { _, isChecked -> mPresenter.saveIPV(isChecked) }

        mPresenter.initConfiguration()
    }

    override fun setConfigTextToSpeech(checked : Boolean) {
        switchVoz.isChecked = checked
    }

    override fun setConfigOutlook(checked : Boolean) {
        switchOutlook.isChecked = checked
    }

    override fun setConfigIPV(checked : Boolean) {
        switchIPV.isChecked = checked
    }
}