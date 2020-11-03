package com.unilever.julia.ui.ipv.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.unilever.julia.R
import com.unilever.julia.data.model.ipv.IpvOfferProduct
import com.unilever.julia.utils.Utils

class OffersProductsAdapter : RecyclerView.Adapter<OffersProductsAdapter.ViewHolder>() {

    private var mDataSet: MutableList<IpvOfferProduct> = arrayListOf()

    fun addItems(items: List<IpvOfferProduct>) {
        mDataSet.clear()
        mDataSet.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.ipv_ofertas_products_holder, parent, false))
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mDataSet[position]
        val context = holder.itemView.context

        holder.tvCode.text = item.code ?: ""
        holder.tvDescription.text = item.description ?: ""

        val colorProgress = Utils.getColorByHexa(item.colorCode ?: "")
        holder.tvScoreEntry.text = context.getString(R.string.ipv_offer_score_entry, item.getTextScore(), item.getTextEntry())
        holder.tvScoreEntry.setTextColor(colorProgress)

        holder.progressBar.isIndeterminate = false
        holder.progressBar.max = 100
        holder.progressBar.progress = item.getScoreRound().toInt()
        Utils.setProgressColor(ContextCompat.getColor(context, R.color.colorChatBg), colorProgress, holder.progressBar)

        holder.tvTarget.text = item.getTextTarget()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCode = itemView.findViewById<TextView>(R.id.tvCode)

        val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)

        val tvScoreEntry = itemView.findViewById<TextView>(R.id.tvScoreEntry)

        val progressBar = itemView.findViewById<ProgressBar>(R.id.progressBar)

        val tvTarget = itemView.findViewById<TextView>(R.id.tvTarget)
    }
}