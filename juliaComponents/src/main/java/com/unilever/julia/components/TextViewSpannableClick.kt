package com.unilever.julia.components

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import org.apache.commons.lang3.StringUtils

class TextViewSpannableClick : AppCompatTextView {

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
        /*
        if (isInEditMode) {
            init(context, attrs)
        } else {
            init(context, null)
        }
        */
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private var textColorLink = ContextCompat.getColor(context, R.color.spannableLink_TextColorLink)

    private fun init(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.TextViewSpannableClick, 0, 0
            )
            try {
                textColorLink = typedArray.getColor(R.styleable.TextViewSpannableClick_txtViewSpanClick_textColorLink, textColorLink)

            } finally {
                typedArray.recycle()
            }
        }
        setTextWithLink(text.toString())
    }

    fun setTextWithLink(text : String) {
        setNewText(parseText(text))
    }

    private fun setNewText(spanText : SpanText) {
        val spannable = SpannableString(spanText.text)
        for (it in spanText.items) {
            spannable.setSpan(CustommSpannableClick(), it.start, it.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        text = spannable
        isClickable = true
        movementMethod = LinkMovementMethod.getInstance()
    }

    private var mListener : Listener? = null

    fun setListener(listener : Listener) {
        mListener = listener
    }

    interface Listener {
        fun onClickLink()
    }

    inner class CustommSpannableClick : ClickableSpan() {
        override fun onClick(textView: View) {
            mListener?.onClickLink()
        }

        override fun updateDrawState(textPaint: TextPaint) {
            textPaint.color = textColorLink
            textPaint.isUnderlineText = true
            textPaint.isFakeBoldText = true
        }
    }

    companion object {
        const val TAG_START = "<link>"
        const val TAG_END = "<link/>"
    }

    inner class SpanText(var text: String, var items: MutableList<SpanItem>)

    inner class SpanItem(var start : Int, var end : Int)

    private fun parseText(text : String): SpanText {
        val spanText = SpanText(text, arrayListOf())
        if (StringUtils.containsAny(text, TAG_START, TAG_END)) {
            val textTrim = StringUtils.trim(text)
            val textWithoutTags = StringUtils.replaceEach(textTrim, arrayOf(TAG_START, TAG_END), arrayOf("", ""))
            val textsBetweenTags = StringUtils.substringsBetween(textTrim, TAG_START, TAG_END)
            if (!textsBetweenTags.isNullOrEmpty()) {
                for (it in textsBetweenTags) {
                    val start = textWithoutTags.indexOf(it)
                    val end = start + it.length
                    spanText.items.add(SpanItem(start, end))
                }
            }
            spanText.text = textWithoutTags
        }
        return spanText
    }
}