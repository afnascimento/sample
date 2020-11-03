package com.unilever.julia.components.boletim

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.unilever.julia.components.R
import com.unilever.julia.components.TagsContainer
import com.unilever.julia.components.TagsModel
import com.unilever.julia.utils.Utils

class BoletimFiltroItem : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var tvTitle : TextView
    private var tvSelected : TextView
    private var tagsContainer : TagsContainer

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val text : String
        val selectedText : String

        val typedArray : TypedArray? = Utils.Styleable.getTypedArray(context, attrs, R.styleable.BoletimFiltroItem)
        try {
            text = Utils.Styleable.getString(typedArray,
                R.styleable.BoletimFiltroItem_bolFtTitle, context.getString(R.string.area_de_atendimento))
            selectedText = Utils.Styleable.getString(typedArray,
                R.styleable.BoletimFiltroItem_bolFtSelected, context.getString(R.string.todos))
        } finally {
            typedArray?.recycle()
        }
        inflate(context, R.layout.boletim_filtro_item, this)

        tvTitle = findViewById(R.id.tvTitle)
        tvSelected = findViewById(R.id.tvAll)
        tagsContainer = findViewById(R.id.tagsContainer)
        tagsContainer.visibility = View.GONE
        tagsContainer.setListener { count: Int ->
            if (count == 0) {
                clear()
            }
        }

        setTitle(text)
        setSelectedText(selectedText)
    }

    fun setTitle(text: String) {
        tvTitle.text = text
    }

    fun setSelectedText(text: String) {
        tvSelected.text = text
    }

    fun addTags(tags : List<TagsModel>) {
        if (tags.isEmpty()) {
            clear()
        } else {
            tvSelected.visibility = View.GONE
            tagsContainer.visibility = View.VISIBLE
            for (it in tags) {
                tagsContainer.addTag(it)
            }
        }
    }

    fun addUniqueTag(tag: TagsModel) {
        tvSelected.visibility = View.GONE
        tagsContainer.visibility = View.VISIBLE
        tagsContainer.clear()
        tagsContainer.addTag(tag)
    }

    fun clear() {
        tvSelected.visibility = View.VISIBLE
        tagsContainer.visibility = View.GONE
        tagsContainer.clear()
    }

    fun getTags() : List<TagsModel> {
        return tagsContainer.tags
    }

    override fun setOnClickListener(listener: OnClickListener?) {
        findViewById<View>(R.id.contentClick).setOnClickListener(listener)
    }
}