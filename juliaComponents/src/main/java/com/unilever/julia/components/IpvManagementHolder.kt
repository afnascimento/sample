package com.unilever.julia.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.unilever.julia.components.adapter.IpvManagementAdapter
import com.unilever.julia.components.model.IpvManagementCard
import com.unilever.julia.utils.Utils

class IpvManagementHolder : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var tvCode : TextView

    private var tvDescription : TextView

    private var tvTarget : TextView

    private var tvScore : TextView

    private var gridItems : GridViewExpandableHeight

    private var buttonRight : View

    private var mAdapter : IpvManagementAdapter

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.ipv_management_holder, this)
        tvCode = findViewById(R.id.tvCode)
        tvDescription = findViewById(R.id.tvDescription)
        tvTarget = findViewById(R.id.tvTarget)
        tvScore = findViewById(R.id.tvScore)
        buttonRight = findViewById(R.id.buttonRight)
        buttonRight.setOnClickListener {
            mListener?.onClickNextContext(mManagement ?: IpvManagementCard())
        }
        mAdapter = IpvManagementAdapter(context)
        gridItems = findViewById(R.id.gridItems)
        gridItems.adapter = mAdapter
    }

    interface Listener {
        fun onClickNextContext(management: IpvManagementCard)
    }

    private var mListener : Listener? = null

    fun setListener(listener : Listener) {
        this.mListener = listener
    }

    private var mManagement: IpvManagementCard? = null

    fun bind(card: IpvManagementCard) {
        mManagement = card
        tvCode.text = card.code
        tvDescription.text = card.name
        tvTarget.text = card.getMetaText()
        tvScore.text = card.getScoreText()
        tvScore.setTextColor(Utils.getColorByHexa(card.getScoreColor()))
        mAdapter.addItems(card.getItemsSorted())
    }
}