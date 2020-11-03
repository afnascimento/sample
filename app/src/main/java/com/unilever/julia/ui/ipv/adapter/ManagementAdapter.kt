package com.unilever.julia.ui.ipv.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unilever.julia.components.IpvManagementHolder
import com.unilever.julia.components.model.IpvManagementCard

class ManagementAdapter(private var mListener : IpvManagementHolder.Listener) : RecyclerView.Adapter<ManagementAdapter.ViewHolder>() {

    private var mDataSet: MutableList<IpvManagementCard> = arrayListOf()

    fun addItems(items: List<IpvManagementCard>) {
        mDataSet.clear()
        mDataSet.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(IpvManagementHolder(parent.context))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mDataSet[position]
        val card = holder.itemView as IpvManagementHolder
        card.bind(item)
        card.setListener(mListener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}