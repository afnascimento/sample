package com.unilever.julia.ui.component

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.unilever.julia.R
import com.unilever.julia.components.ButtonsBottom
import com.unilever.julia.data.model.StatusPedidoModel
import com.unilever.julia.utils.Utils

class ChatMainButtonsShare : RelativeLayout, ButtonsBottom.Listener {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var buttonsBottom : ButtonsBottom

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.chat_main_buttons_shared, this)

        buttonsBottom = findViewById(R.id.buttonsShared)
        buttonsBottom.setListener(this)
    }

    override fun onClickLeft() {
        reset()
        mListener?.onClickCancelShare()
    }

    override fun onClickRight() {
        if (mItems.isEmpty()) {
            Utils.showToast(context, "Selecione um pedido")
        } else {
            mListener?.onConfirmShare(mItems)
        }
    }

    fun reset() {
        mItems.clear()
        mComponent?.clearAllSelectedCards()
    }

    private var mListener : Listener? = null

    fun setListener(listener : Listener) {
        this.mListener = listener
    }

    interface Listener {
        fun onClickCancelShare()

        fun onConfirmShare(items : MutableList<StatusPedidoModel.Item>)
    }

    private var mItems : MutableList<StatusPedidoModel.Item> = arrayListOf()

    fun addItem(item: StatusPedidoModel.Item) {
        mItems.add(item)
    }

    fun removeItem(item: StatusPedidoModel.Item) {
        mItems.remove(item)
    }

    private var mComponent: JuliaStatusChat? = null

    fun setComponent(component: JuliaStatusChat) {
        mComponent = component
    }
}