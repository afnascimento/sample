package com.unilever.julia.ui.inovacao.tradestoryEvaluation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.unilever.julia.R
import com.unilever.julia.components.ButtonsBottom
import com.unilever.julia.data.model.inovacaoDetalhe.Resumo
import javax.inject.Inject
import com.unilever.julia.ui.base.*
import com.unilever.julia.utils.RedirectIntents
import kotlinx.android.synthetic.main.innovation_tradestory_evaluation.*
import kotlinx.android.synthetic.main.innovation_tradestory_evaluation_content.*

class TradeStoryEvaluationActivity : BaseViewActivity(), TradeStoryEvaluationView, ButtonsBottom.Listener {

    @Inject
    lateinit var mPresenter: TradeStoryEvaluationPresenter<TradeStoryEvaluationView, TradeStoryEvaluationInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.innovation_tradestory_evaluation)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        toolbar.setClickListenerBackIcon(View.OnClickListener {
            onBackPressed()
        })

        buttonsBottom.setListener(this)
    }

    override fun onClickLeft() {
        onBackPressed()
    }

    override fun onClickRight() {
        val identifier : String = intent.getStringExtra(RedirectIntents.EXTRA_IDENTIFIER)
        val stars = rating.rating
        val description = tvDescription.text.toString()
        mPresenter.sendEvaluationTradeStory("", identifier, stars.toInt(), description)
    }

    companion object {
        const val EXTRA_TRADESTORY = "EXTRA_TRADESTORY"
        const val REQUEST_CODE = 1
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    override fun backSuccess(tradestory : Resumo.TradeStory) {
        val intent = Intent()
        intent.putExtra(EXTRA_TRADESTORY, tradestory)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}