package com.unilever.julia.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.unilever.julia.R
import com.unilever.julia.data.enums.StatusCardEnum
import com.unilever.julia.data.model.IStatusPedido
import com.unilever.julia.data.model.StatusPedidoItem
import com.unilever.julia.data.model.StatusPedidoModel
import com.unilever.julia.ui.component.JuliaStatusCard

class StatusPedidoAdapter(private var mListener: Listener) : RecyclerView.Adapter<StatusPedidoAdapter.ViewHolder>() {

    var mDataSet : MutableList<IStatusPedido> = arrayListOf()

    fun addItems(items : MutableList<IStatusPedido>) {
        mDataSet.clear()
        mDataSet.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    override fun getItemViewType(position: Int): Int {
        return mDataSet[position].getType().getId()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == StatusCardEnum.CARD.getId()) {
            return CardHolder(JuliaStatusCard(parent.context, null, JuliaStatusCard.DISABLED_SHARE))
        } else if (viewType == StatusCardEnum.ITEM_TOP.getId()) {
            return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.julia_status_detalhe_card_item_v3_top, parent, false), mListener)
        } else if (viewType == StatusCardEnum.ITEM_MIDDLE.getId()) {
            return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.julia_status_detalhe_card_item_v3_middle, parent, false), mListener)
        } else {
            return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.julia_status_detalhe_card_item_v3_bottom, parent, false), mListener)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mDataSet[position])
    }

    abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(model: IStatusPedido)
    }

    class CardHolder(itemView: View) : ViewHolder(itemView) {

        override fun bind(model: IStatusPedido) {
            val it : StatusPedidoModel.Item = model as StatusPedidoModel.Item

            val component : JuliaStatusCard = itemView as JuliaStatusCard
            component.setItem(it)
            component.setVisibleButton(false)
        }
    }

    interface Listener {
        fun onClickArrow(model: StatusPedidoItem)
    }

    class ItemViewHolder(itemView: View, private var listener: Listener) : ViewHolder(itemView) {

        var tvTitle : TextView = itemView.findViewById(R.id.tvTitle)

        var tvCodeProduct : TextView = itemView.findViewById(R.id.tvCodeProduct)

        var tvValue : TextView = itemView.findViewById(R.id.tvValue)

        var arrowRight : View = itemView.findViewById(R.id.arrowRight)

        override fun bind(model: IStatusPedido) {
            val it : StatusPedidoItem = model as StatusPedidoItem
            tvTitle.text = it.content.produto
            tvCodeProduct.text = it.content.codSKU
            tvValue.text = itemView.context.getString(R.string.status_pedido_card_valor, it.content.valor, it.content.quantidade)
            arrowRight.setOnClickListener {
                listener.onClickArrow(model)
            }
        }
    }
}