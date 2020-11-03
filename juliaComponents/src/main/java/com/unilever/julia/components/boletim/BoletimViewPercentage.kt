package com.unilever.julia.components.boletim

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.transition.Fade
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.transitionseverywhere.extra.Scale
import com.unilever.julia.components.R
import com.unilever.julia.components.model.BoletimItem
import com.unilever.julia.utils.NumberUtils
import com.unilever.julia.utils.Utils

class BoletimViewPercentage : RelativeLayout, Runnable {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var transitionsContainer : ViewGroup

    private var tvValue : TextView

    private var tvPercentage : TextView

    private var progressBar : ProgressBar

    private var tvTitle : TextView

    private var tvDescription : TextView

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.boletim_card_chat_progress, this)

        val contentText = findViewById<View>(R.id.contentText)
        tvTitle = contentText.findViewById(R.id.tvLeft)
        tvDescription = contentText.findViewById(R.id.tvRight)

        transitionsContainer = findViewById(R.id.transitionsContainer)
        progressBar = findViewById(R.id.progressBar)
        tvValue = findViewById(R.id.tvValue)
        tvPercentage = findViewById(R.id.tvPercentage)

        setupProgress()
    }

    private fun setupProgress() {
        progressBar.isIndeterminate = false
        progressBar.max = 100
        progressBar.progress = 0
    }

    private var mProgress: Double = 0.0

    private var mVisiblePercent : Boolean = true

    fun bind(percent : BoletimItem.Percentage, visiblePercent : Boolean) {
        tvTitle.text = percent.title
        tvDescription.text = percent.description
        tvValue.text = NumberUtils.toCurrencyBrazil(percent.value, false)

        val colorProgress = Utils.getColorByHexa(percent.color)

        Utils.setProgressColor(ContextCompat.getColor(context, R.color.colorChatBg), colorProgress, progressBar)

        mVisiblePercent = visiblePercent

        tvPercentage.text = Utils.getTextPercent(percent.percentage)
        tvPercentage.setTextColor(colorProgress)
        tvPercentage.visibility = View.GONE

        mProgress = percent.percentage

        postDelayed(this, 50)
    }

    override fun run() {
        val animation = ObjectAnimator.ofInt(progressBar, "progress", progressBar.progress, mProgress.toInt())
        animation.duration = 1000
        animation.interpolator = DecelerateInterpolator()
        animation.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                showText()
            }
        })
        animation.start()
    }

    private fun showText() {
        if (mVisiblePercent) {
            val set = TransitionSet()
                .addTransition(Scale(0.7f))
                .addTransition(Fade())
                .setInterpolator(FastOutLinearInInterpolator())
            TransitionManager.beginDelayedTransition(transitionsContainer, set)
            tvPercentage.visibility = View.VISIBLE
        }
    }
}