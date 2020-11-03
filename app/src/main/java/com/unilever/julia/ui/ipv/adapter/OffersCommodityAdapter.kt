package com.unilever.julia.ui.ipv.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.unilever.julia.R
import com.unilever.julia.components.ButtonIconRound
import com.unilever.julia.data.model.ipv.IpvOffer

class OffersCommodityAdapter(private var mListener : Listener) : RecyclerView.Adapter<OffersCommodityAdapter.ViewHolder>() {

    interface Listener {
        fun onItemClick(item: IpvOffer)
    }

    private var mDataSet: MutableList<IpvOffer> = arrayListOf()

    fun addItems(items: List<IpvOffer>) {
        mDataSet.clear()
        mDataSet.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.ipv_ofertas_holder, parent, false))
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mDataSet[position]

        holder.buttonIconRound.setIcon(item.icon ?: "")

        holder.tvCommodity.text = item.commodity ?: ""

        val onClickListener = View.OnClickListener { mListener.onItemClick(item) }
        holder.buttonRight.setOnClickListener(onClickListener)
        holder.contentClick.setOnClickListener(onClickListener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val buttonIconRound = itemView.findViewById<ButtonIconRound>(R.id.buttonIconRound)
        val tvCommodity = itemView.findViewById<TextView>(R.id.tvCommodity)
        val buttonRight = itemView.findViewById<View>(R.id.buttonArrow)
        val contentClick = itemView.findViewById<View>(R.id.contentClick)
    }
}