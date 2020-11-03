package com.unilever.julia.components.innovations

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.unilever.julia.components.R
import com.unilever.julia.video.ParserJsonSample

class InnovationsMidias : RelativeLayout {

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        if (isInEditMode) {
            init(context, null)
        } else {
            init(context, attrs)
        }
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    lateinit var tvTitle : TextView

    lateinit var contentItems : LinearLayout

    private fun init(context: Context, attrs: AttributeSet?) {
        inflate(context, R.layout.innovation_v2_midias, this)

        tvTitle = findViewById(R.id.tvTitle)
        contentItems = findViewById(R.id.contentItems)

        if (attrs == null) {
            val items = Gson().fromJson(
                context.getString(R.string.midias_innovations_items),
                Array<Item>::class.java)
            addMidias(items.toMutableList())
        }
    }

    data class Item(
        @SerializedName("title")
        val title : String = "",
        @SerializedName("description")
        val description : String = "",
        @SerializedName("url")
        val url : String = ""
    )

    private fun getMidiaView(item: Item): View {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.innovation_v2_midias_card, contentItems, false)

        val button = view.findViewById<ImageView>(R.id.button)
        button.setOnClickListener {
            val urlVideo = item.url.trim()
            val json = "[\n" +
                    "  {\n" +
                    "    \"name\": \"${item.title}\",\n" +
                    "    \"samples\": [\n" +
                    "      {\n" +
                    "        \"name\": \"${item.description}\",\n" +
                    "        \"uri\": \"${urlVideo}\"\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  }\n" +
                    "]"
            val intent = ParserJsonSample().buildIntent(context, json)
            mListener?.onPlayerClick(intent)
        }

        setTextInTextView(view.findViewById(R.id.tvTitle), item.title)
        setTextInTextView(view.findViewById(R.id.tvDescription), item.description)

        return view
    }

    private fun setTextInTextView(textView: TextView, text: String) {
        val pText = text.trim()
        if (pText.isEmpty()) {
            textView.visibility = View.GONE
        } else {
            textView.visibility = View.VISIBLE
            textView.text = pText
        }
    }

    fun setTitle(title: String) {
        tvTitle.text = title
    }

    fun addMidias(items : MutableList<Item>) {
        contentItems.removeAllViews()
        var index = 0

        val lastIndex = (items.size - 1)
        for (it in items) {
            val view = getMidiaView(it)
            if (index == 0) {
                view.layoutParams = getMargins(
                    resources.getDimension(R.dimen.innovation_v2_margin_start),
                    0f,
                    0f,
                    0f)
            } else if (index == lastIndex) {
                view.layoutParams = getMargins(
                    resources.getDimension(R.dimen.innovation_v2_margin_end),
                    0f,
                    resources.getDimension(R.dimen.innovation_v2_margin_end),
                    0f)
            } else {
                view.layoutParams = getMargins(
                    resources.getDimension(R.dimen.innovation_v2_margin_end),
                    0f,
                    0f,
                    0f)
            }

            contentItems.addView(view)
            index += 1
        }
    }

    private fun getMargins(left: Float, top: Float, right: Float, bottom: Float): LayoutParams {
        val params = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        params.setMargins(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
        return params
    }

    //private fun getDimension(value: Float): Int {
    //    return Utils.getDimension(context.resources, value, TypedValue.COMPLEX_UNIT_DIP).toInt()
    //}

    interface Listener {
        fun onPlayerClick(intent: Intent)
    }

    private var mListener : Listener? = null

    fun setListener(listener : Listener) {
        mListener = listener
    }
}