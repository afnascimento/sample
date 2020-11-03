package com.unilever.julia.components

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.widget.TextView
import com.unilever.julia.components.adapter.IpvManagementAdapter
import com.unilever.julia.components.model.IpvManagementCard
import com.unilever.julia.utils.Utils

class IpvManagementHeader : ConstraintLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var tvCode : TextView

    private var tvDescription : TextView

    private var tvTarget : TextView

    private var tvScore : TextView

    private var gridItems : GridViewExpandableHeight

    private var mAdapter : IpvManagementAdapter

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.ipv_management_header, this)
        tvCode = findViewById(R.id.tvCode)
        tvDescription = findViewById(R.id.tvDescription)
        tvTarget = findViewById(R.id.tvTarget)
        tvScore = findViewById(R.id.tvScore)

        gridItems = findViewById(R.id.gridItems)
        mAdapter = IpvManagementAdapter(context)
        gridItems.adapter = mAdapter
    }

    fun bind(card: IpvManagementCard) {
        tvCode.text = card.getCodeText()
        tvDescription.text = card.name
        //setMetaScore("META: "+card.getMetaText(), card.getMetaColor(), tvTarget)
        tvTarget.text = "META: "+card.getMetaText()
        setMetaScore("SCORE: "+card.getScoreText(), card.getScoreColor(), tvScore)
        mAdapter.addItems(card.getItemsSorted())
    }

    private fun setMetaScore(text: String, color: String, textView: TextView) {
        textView.text = text
        Utils.setColorDrawable(color, textView.background)
    }
}