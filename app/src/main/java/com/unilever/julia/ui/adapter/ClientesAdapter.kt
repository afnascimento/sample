package com.unilever.julia.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.unilever.julia.R
import com.unilever.julia.data.model.ClienteModel
import com.unilever.julia.utils.Utils
import com.unilever.julia.utils.setAbreviateText

class ClientesAdapter(private var mListener : Listener) : androidx.recyclerview.widget.RecyclerView.Adapter<ClientesAdapter.ViewHolder>() {

    interface Listener {
        fun onItemClick(item: ClienteModel)
    }

    private var mDataSet: MutableList<ClienteModel> = arrayListOf()

    fun addItems(items: MutableList<ClienteModel>) {
        mDataSet.clear()
        mDataSet.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.clientes_item_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mDataSet[position]

        Utils.setColorDrawable(item.customer?.color ?: "", holder.buttonRound.background)

        holder.tvTextButton.text = Utils.getTextPercent(item.customer?.value ?: 0.0)

        val name : String = item.customer?.getNameWithCode() ?: ""
        holder.tvNomeCliente.text = name
        holder.tvNomeCliente.setAbreviateText(1)

        val adress : String =  item.customer?.getAddress() ?: ""
        holder.tvEndereco.text = adress

        holder.buttonRight.setOnClickListener {
            mListener.onItemClick(item)
        }
    }

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val buttonRound = itemView.findViewById<View>(R.id.buttonRound)
        val tvTextButton = itemView.findViewById<TextView>(R.id.tvTextButton)
        val tvNomeCliente = itemView.findViewById<TextView>(R.id.tvNomeCliente)
        val tvEndereco = itemView.findViewById<TextView>(R.id.tvEndereco)
        val buttonRight = itemView.findViewById<View>(R.id.buttonRight)
    }
}