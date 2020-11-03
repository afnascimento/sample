package com.unilever.julia.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.unilever.julia.R
import com.unilever.julia.data.model.PedidoClienteResponse
import com.unilever.julia.utils.NumberUtils
import org.apache.commons.lang3.StringUtils

class PedidosClienteAdapter(var mListener : Listener) : RecyclerView.Adapter<PedidosClienteAdapter.ViewHolder>() {

    interface Listener {
        fun onItemClick(item: PedidoClienteResponse)
    }

    var mDataSet: MutableList<PedidoClienteResponse> = arrayListOf()

    fun addItems(items: MutableList<PedidoClienteResponse>) {
        mDataSet.clear()
        mDataSet.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.julia_pedido_cliente_item, parent, false))
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mDataSet[position]

        holder.contentClick.setOnClickListener {
            mListener.onItemClick(item)
        }
        holder.tvTextRound.text = StringUtils.trimToEmpty(item.division)
        holder.tvData.text = StringUtils.trimToEmpty(item.dateCreation)
        holder.tvNumPedido.text = StringUtils.trimToEmpty(item.numeroPedido)
        holder.tvValor.text = NumberUtils.toCurrencyBrazil(item.getValue(), true)
    }

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        val contentClick = itemView.findViewById<View>(R.id.contentClick)

        val tvTextRound = itemView.findViewById<TextView>(R.id.tvTextRound)

        val tvData = itemView.findViewById<TextView>(R.id.tvData)

        val tvNumPedido = itemView.findViewById<TextView>(R.id.tvNumPedido)

        val tvValor = itemView.findViewById<TextView>(R.id.tvValor)
    }
}