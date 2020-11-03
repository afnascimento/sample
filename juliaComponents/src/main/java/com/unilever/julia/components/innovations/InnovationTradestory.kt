package com.unilever.julia.components.innovations

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.ashokslsk.androidabcd.squarerating.SquareRatingView
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.unilever.julia.components.R
import com.unilever.julia.components.TextViewSpannableClick
import com.unilever.julia.utils.bind
import com.unilever.julia.utils.isValidURL
import com.unilever.julia.utils.toast

class InnovationTradestory : RelativeLayout {

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private val banner by bind<View>(R.id.banner)

    private val tvTitle by bind<TextView>(R.id.tvTitle)

    private val tvTitleCard by bind<TextView>(R.id.tvTitleCard)

    private val rating by bind<SquareRatingView>(R.id.rating)

    private val tvNumDownloads by bind<TextView>(R.id.tvNumDownloads)

    private val imgButtonDownload by bind<ImageView>(R.id.imgButtonDownload)

    private val btnDownload by bind<View>(R.id.btnDownload)

    private val tvTextEvaluation by bind<TextViewSpannableClick>(R.id.tvTextEvaluation)

    @Suppress("UNUSED_PARAMETER")
    private fun init(context: Context, attrs: AttributeSet?) {
        inflate(context, R.layout.innovation_v2_tradestory, this)
        btnDownload.setOnClickListener {
            if (urlDownload.isEmpty()) {
                toast(R.string.tradestory_download_notfound)
                return@setOnClickListener
            }
            if (!urlDownload.isValidURL()) {
                toast(R.string.tradestory_download_invalid_url)
                return@setOnClickListener
            }
            mListener?.downloadTradestory(urlDownload)
        }
        tvTextEvaluation.setListener(object : TextViewSpannableClick.Listener {
            override fun onClickLink() {
                mListener?.redirectEvaluationTradestory()
            }
        })
        startAnimationIconDownload()
    }

    private var title: String = ""
    private var evaluation: Int = 0
    private var numDownload: Int = 0
    private var urlDownload: String = ""
    private var isNewDownload : Boolean = false

    fun setValues(title: String, evaluation: Int, numDownload: Int, urlDownload: String, isNewDownload : Boolean) {
        setTitle(title)
        setEvaluation(evaluation)
        setDownload(numDownload, urlDownload)
        this.isNewDownload = isNewDownload
        updateLayout()
    }

    private fun setTitle(text: String) {
        this.title = text
        tvTitle.text = text
        tvTitleCard.text = text
    }

    private fun setEvaluation(evaluation: Int) {
        this.evaluation = evaluation
        rating.rating = evaluation.toFloat()
    }

    private fun setDownload(numDownload: Int, urlDownload: String) {
        this.numDownload = numDownload
        this.urlDownload = urlDownload
        tvNumDownloads.text = context.getString(R.string.tradestory_downloads, numDownload.toString())
    }

    private fun updateLayout() {
        banner.visibility = View.GONE
        tvTextEvaluation.visibility = View.GONE

        if (evaluation > 0 && isNewDownload) {
            return
        }
        if (isNewDownload) {
            banner.visibility = View.VISIBLE

            tvTextEvaluation.visibility = View.VISIBLE
            tvTextEvaluation.setTextWithLink(context.getString(R.string.tradestory_text_evaluation))
            return
        }
        if (evaluation == 0) {
            tvTextEvaluation.visibility = View.VISIBLE
            tvTextEvaluation.setTextWithLink(context.getString(R.string.tradestory_text_click))
        }
    }

    private fun startAnimationIconDownload() {
        YoYo.with(Techniques.BounceInUp)
            .duration(1000)
            .repeat(2)
            .playOn(imgButtonDownload)
    }

    interface Listener {
        fun downloadTradestory(url: String)
        fun redirectEvaluationTradestory()
    }

    private var mListener : Listener? = null

    fun setListener(listener : Listener) {
        this.mListener = listener
    }
}