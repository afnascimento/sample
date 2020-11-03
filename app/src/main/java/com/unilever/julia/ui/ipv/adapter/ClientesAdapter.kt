package com.unilever.julia.ui.ipv.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.unilever.julia.R
import com.unilever.julia.components.ButtonTextRound
import com.unilever.julia.data.model.ipv.IpvClient
import com.unilever.julia.utils.setAbreviateText

class ClientesAdapter(private var mListener : Listener) : RecyclerView.Adapter<ClientesAdapter.AbstractViewHolder>() {

    private var isTextViewScoreVisible = true

    fun setTextViewScoreVisible(visible: Boolean) {
        this.isTextViewScoreVisible = visible
    }

    private var isButtonRightVisible = true

    fun setButtonRightVisible(visible: Boolean) {
        this.isButtonRightVisible = visible
    }

    interface Listener {
        fun onItemClick(item: IpvClient)
    }

    private var mDataSet: MutableList<IpvClient> = arrayListOf()

    fun addItems(items: List<IpvClient>) {
        mDataSet.clear()
        mDataSet.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder {
        return if (isTextViewScoreVisible) {
            ViewHolderWithTextViewScore(LayoutInflater.from(parent.context).inflate(
                R.layout.ipv_clientes_item_holder, parent, false))
        } else {
            ViewHolderWithoutTextViewScore(LayoutInflater.from(parent.context).inflate(
                R.layout.ipv_clientes_item_holder_2, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    override fun onBindViewHolder(holder: AbstractViewHolder, position: Int) {
        holder.bind(mDataSet[position], isTextViewScoreVisible, isButtonRightVisible, mListener)
    }

    abstract class AbstractViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contentClick = itemView.findViewById<View>(R.id.contentClick)
        val buttonRound = itemView.findViewById<ButtonTextRound>(R.id.buttonRound)
        val tvName = itemView.findViewById<TextView>(R.id.tvNameClient)
        val tvAddress = itemView.findViewById<TextView>(R.id.tvAddress)
        val buttonRight = itemView.findViewById<View>(R.id.buttonRight)

        abstract fun getTextViewScore(): TextView?

        fun bind(item: IpvClient, isTextViewScoreVisible : Boolean, isButtonRightVisible: Boolean,  listener : Listener) {
            buttonRound.setColorBackground(item.colorCode ?: "")
            buttonRound.setText(item.getCommodityText())

            tvName.text = item.getNameWithCode()
            if (isButtonRightVisible) {
                tvName.setAbreviateText(1)
            }

            tvAddress.text = item.getAddress()
            tvAddress.setAbreviateText(1)

            if (isTextViewScoreVisible) {
                val context = itemView.context
                getTextViewScore()?.text = context.getString(R.string.ipv_score, item.getScoreText())
                getTextViewScore()?.visibility = View.VISIBLE
            } else {
                getTextViewScore()?.visibility = View.GONE
            }

            if (isButtonRightVisible) {
                contentClick.setOnClickListener {
                    listener.onItemClick(item)
                }
                buttonRight.setOnClickListener {
                    listener.onItemClick(item)
                }
                buttonRight.visibility = View.VISIBLE
            } else {
                buttonRight.visibility = View.GONE
            }
        }
    }

    class ViewHolderWithTextViewScore(itemView: View) : AbstractViewHolder(itemView) {

        override fun getTextViewScore(): TextView? {
            return itemView.findViewById(R.id.tvScore)
        }
    }

    class ViewHolderWithoutTextViewScore(itemView: View) : AbstractViewHolder(itemView) {

        override fun getTextViewScore(): TextView? {
            return null
        }
    }
}