package com.unilever.julia.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.unilever.julia.R
import com.unilever.julia.data.model.notificacao.Notification
import com.unilever.julia.data.model.notificacao.holder.NotificationViewType
import com.unilever.julia.data.model.notificacao.holder.TitleViewType

class NotificationAdapter(var mListener : Listener) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    interface Listener {
        fun onItemClick(item: Notification, position: Int)
    }

    var mDataSet: MutableList<NotificationViewType> = arrayListOf()

    fun addItems(items: List<NotificationViewType>) {
        mDataSet.clear()
        mDataSet.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return mDataSet[position].getType().ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (NotificationViewType.Type.values()[viewType]) {
            NotificationViewType.Type.TITLE -> TitleViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.notificacao_item_header, parent, false))
            NotificationViewType.Type.CARD -> CardViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.notificacao_item_layout, parent, false),
                mListener
            )
        }
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mDataSet[position], position)
    }

    abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: NotificationViewType, position: Int)
    }

    class TitleViewHolder(itemView: View) : ViewHolder(itemView) {

        val tvHeader = itemView.findViewById<TextView>(R.id.tvNotificacaoTitle)

        override fun bind(item: NotificationViewType, position: Int) {
            val model : TitleViewType = item as TitleViewType
            tvHeader.text = model.title
        }
    }

    class CardViewHolder(itemView: View, val mListener : Listener) : ViewHolder(itemView) {

        val content = itemView.findViewById<View>(R.id.contentClickNotification)

        val rlBadge = itemView.findViewById<View>(R.id.badgeRound)

        val tvData = itemView.findViewById<TextView>(R.id.tvNotificacaoData)

        val tvDescription = itemView.findViewById<TextView>(R.id.tvNotificacaoDescription)

        override fun bind(item: NotificationViewType, position: Int) {
            val model : Notification = item as Notification
            content.setOnClickListener {
                mListener.onItemClick(model, position)
            }

            tvData.text = model.getDateString()
            tvDescription.text = model.description ?: ""

            val context = itemView.context
            rlBadge.background =
                if (model.read == true) context.getDrawable(R.drawable.bg_round_badge_grey)
                else context.getDrawable(R.drawable.bg_round_badge_blue)
        }
    }
}