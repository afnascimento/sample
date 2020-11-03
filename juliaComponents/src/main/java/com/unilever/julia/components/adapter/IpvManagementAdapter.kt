package com.unilever.julia.components.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.unilever.julia.components.R
import com.unilever.julia.components.model.IpvManagementCard
import com.unilever.julia.utils.Utils

class IpvManagementAdapter(var mContext: Context) : BaseAdapter() {

    private var mData: MutableList<IpvManagementCard.Item> = arrayListOf()

    fun addItems(items: List<IpvManagementCard.Item>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): IpvManagementCard.Item {
        return mData[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return mData.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.ipv_item_layout, parent, false)
            view.tag = ViewHolder(view)
        } else {
            view = convertView
        }
        val holder : ViewHolder = view.tag as ViewHolder

        val item : IpvManagementCard.Item = getItem(position)
        Utils.setColorDrawable(item.colorCode, holder.buttonRound.background)
        holder.tvPercentage.text = Utils.getTextPercent(item.percentage)
        holder.tvDescription.text = item.description

        return view
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val buttonRound = itemView.findViewById<View>(R.id.viewColor)
        val tvPercentage = itemView.findViewById<TextView>(R.id.tvPercentage)
        val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)
    }
}