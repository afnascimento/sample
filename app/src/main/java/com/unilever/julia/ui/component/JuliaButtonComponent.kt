package com.unilever.julia.ui.component

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.unilever.julia.R
import com.unilever.julia.data.model.AdicionarAgendaModel
import com.unilever.julia.data.model.ButtonModel
import com.unilever.julia.data.model.ChitChatModel
import com.unilever.julia.components.JuliaAnswerComponent

class JuliaButtonComponent : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var julAnswerCard : JuliaAnswerComponent

    private var contentButtonItems : LinearLayout

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.julia_chat_button, this)

        julAnswerCard = findViewById(R.id.julAnswerCard)

        contentButtonItems = findViewById(R.id.contentButtonItems)
        /*
        var cardText = context.getString(R.string.julia_chat_msg)

        val items = arrayListOf<ButtonModel.Item>()

        if (attrs != null) {
            val typedArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.JuliaButtonComponent, 0, 0
            )
            var jsonItems = ""
            try {
                cardText = typedArray.getString(R.styleable.JuliaButtonComponent_julBtCompText) ?: cardText
                val julBtCompItems = typedArray.getResourceId(R.styleable.JuliaButtonComponent_julBtCompItems, 0)
                if (julBtCompItems != 0) {
                    jsonItems = context.getString(julBtCompItems)
                }
            } finally {
                typedArray.recycle()
            }

            if (StringUtils.isNotEmpty(jsonItems)) {
                val gson = Gson()
                val array = gson.fromJson(jsonItems, Array<ButtonModel.Item>::class.java)
                for (it in array) {
                    items.add(it)
                }
            }
        }

        inflate(context, R.layout.julia_chat_button, this)

        julAnswerCard = findViewById(R.id.julAnswerCard)

        contentButtonItems = findViewById(R.id.contentButtonItems)

        if (attrs != null) {
            setText(cardText)
            addButtonItems(items, object : ButtonComponent.Listener {
                override fun onButtonClickListener(text: String, url: String, intencao: String) {

                }
            })
        }
        */
    }

    fun setText(text : String) {
        julAnswerCard.setText(text)
    }

    fun addButtonItems(items : MutableList<ButtonModel.Item>, listener: ButtonComponent.Listener) {
        contentButtonItems.removeAllViews()
        for (it in items) {
            val button = ButtonComponent(context)
            button.setIcon(it.icon)
            button.setColor(it.color)
            button.setText(it.text)
            button.setIntencao(it.intencao)
            button.setListener(listener)
            contentButtonItems.addView(button)
        }
    }

    fun addChitChatItems(items : MutableList<ChitChatModel.Item>, listener: ButtonComponent.Listener) {
        contentButtonItems.removeAllViews()
        for (it in items) {
            val button = ButtonComponent(context)
            button.setColor("#9CA7AC")
            button.setVisibilityIcon(false)
            button.setText(it.text)
            button.setUrl(it.url)
            button.setListener(listener)
            contentButtonItems.addView(button)
        }
    }

    fun addAdicionarAgendaItems(items : MutableList<AdicionarAgendaModel.Item>, listener: ButtonComponent.AdicionarAgendaListener) {
        contentButtonItems.removeAllViews()
        for (it in items) {
            val button = ButtonComponent(context)
            button.setColor("#9CA7AC")
            button.setVisibilityIcon(false)
            button.setText(it.title)
            button.setListener(listener)
            button.setAdicionarAgendaModel(it)
            contentButtonItems.addView(button)
        }
    }
}