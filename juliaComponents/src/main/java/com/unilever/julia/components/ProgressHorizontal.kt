package com.unilever.julia.components

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.unilever.julia.utils.Utils
import android.view.animation.DecelerateInterpolator
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.transition.Fade
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.transitionseverywhere.extra.Scale

class ProgressHorizontal : RelativeLayout, Runnable {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var tvTextPercent : TextView

    private var progressBar : ProgressBar

    private var transitionsContainer : ViewGroup

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.progress_horizontal, this)
        this.tvTextPercent = findViewById(R.id.tvTextPercent)
        this.progressBar = findViewById(R.id.progressBar)
        this.transitionsContainer = findViewById(R.id.transitionsContainer)

        setupProgress()
    }

    private fun setupProgress() {
        progressBar.isIndeterminate = false
        progressBar.max = 100
        progressBar.progress = 0
    }

    //private fun setProgressColor(@ColorInt color: Int) {
    //    Utils.setProgressColor(ContextCompat.getColor(context, R.color.colorWhite), color, progressBar)
    //}

    private var mProgress: Double = 0.0

    private var mIsVisibleText = true

    fun init(percent: Double, color: String, isVisibleText: Boolean) {
        var colorProgress = Utils.getColorByHexa(color)
        if (colorProgress == 0) {
            colorProgress = ContextCompat.getColor(context, R.color.colorOrange)
        }
        Utils.setProgressColor(ContextCompat.getColor(context, R.color.colorWhite), colorProgress, progressBar)

        tvTextPercent.text = Utils.getTextPercent(percent)
        tvTextPercent.setTextColor(colorProgress)
        tvTextPercent.visibility = View.GONE

        setupProgress()
        mProgress = percent

        mIsVisibleText = isVisibleText
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
        if (mIsVisibleText) {
            val set = TransitionSet()
                .addTransition(Scale(0.7f))
                .addTransition(Fade())
                .setInterpolator(FastOutLinearInInterpolator())
            TransitionManager.beginDelayedTransition(transitionsContainer, set)
            tvTextPercent.visibility = View.VISIBLE
        }
    }
}