package com.unilever.julia.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.unilever.julia.R
import com.unilever.julia.components.JuliaTextViewIcon
import com.unilever.julia.components.enums.IconEnums
import com.unilever.julia.logger.Logger

class FeedbackButtonsLike : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    var tvTitle : TextView

    var btnLike : View

    var tvIconLike : JuliaTextViewIcon

    var btnDislike : View

    var tvIconDislike : JuliaTextViewIcon

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.feedback_buttons_like, this)

        tvTitle = findViewById(R.id.tvTitleButtonsFeedback)

        btnLike = findViewById<View>(R.id.btnLike)
        btnLike.setOnClickListener {
            setLike(true)
            onRefreshButtons()
            isClicked = false
        }
        tvIconLike = btnLike.findViewById(R.id.tvIconLike)

        btnDislike = findViewById<View>(R.id.btnDislike)
        btnDislike.setOnClickListener {
            setLike(false)
            onRefreshButtons()
            isClicked = false
            mListener?.onDislike()
        }
        tvIconDislike = btnDislike.findViewById(R.id.tvIconDislike)

        onRefreshButtons()
    }

    private var isClicked = true

    private var mButtons : Int = 0

    private fun setLike(isLike : Boolean) {
        if (isLike) {
            mButtons = 1
        } else {
            mButtons = 2
        }
    }

    fun onRefreshButtons() {
        Logger.debug("ChatMainAdapter", "onRefreshButtons() isClicked = $isClicked mButtons = $mButtons")
        if (!isClicked) {
            return
        }
        if (mButtons == 0) {
            tvTitle.text = context.getString(R.string.consegui_te_ajudar)

            tvIconLike.setIcon(IconEnums.LIKE)
            btnLike.visibility = View.VISIBLE

            tvIconDislike.setIcon(IconEnums.DISLIKE)
            btnDislike.visibility = View.VISIBLE

        } else if (mButtons == 1) {
            tvTitle.text = context.getString(R.string.feedback_like)
            tvIconLike.setIcon(IconEnums.LIKE_SELECT)
            btnDislike.visibility = View.GONE
        } else {
            tvIconDislike.setIcon(IconEnums.DISLIKE_SELECT)
            btnLike.visibility = View.GONE
        }
    }

    private var mListener : Listener? = null

    fun setListener(listener : Listener) {
        mListener = listener
    }

    interface Listener {
        fun onDislike()
    }
}