package com.unilever.julia.ui.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.facebook.shimmer.ShimmerFrameLayout
import com.unilever.julia.R
import com.unilever.julia.data.model.InnovationProjectsModel
import com.unilever.julia.components.CardViewImage
import com.unilever.julia.glide.GlideApp

class ProjectsAdapter(val mFragment : Fragment, val mListener : Listener) : RecyclerView.Adapter<ProjectsAdapter.ViewHolder>() {

    interface Listener {
        fun onClickListener(item: InnovationProjectsModel.Item)
    }

    private var mDataSet : MutableList<InnovationProjectsModel.Item> = arrayListOf()

    fun addItems(items : MutableList<InnovationProjectsModel.Item>) {
        mDataSet.clear()
        mDataSet.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.innovation_v2_product_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mDataSet[position]

        holder.contentClick.setOnClickListener {
            mListener.onClickListener(item)
        }

        GlideApp
            .with(mFragment)
            .load(item.image)
            .fitCenter()
            .placeholder(R.drawable.ic_image_loading)
            .error(R.drawable.ic_image_error)
            .fallback(R.drawable.ic_image_error)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(object : DrawableImageViewTarget(holder.cardImage.getImageView()) {

                override fun onLoadStarted(placeholder: Drawable?) {
                    holder.contentShimmer.startShimmer()
                    super.onLoadStarted(placeholder)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    holder.contentShimmer.stopShimmer()
                    super.onLoadFailed(errorDrawable)
                }

                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    holder.contentShimmer.stopShimmer()
                    super.onResourceReady(resource, transition)
                }
            })

        if (item.isTitle) {
            holder.tvMonthYear.visibility = View.VISIBLE
            holder.tvMonthYear.text = item.getMonthYearAsText()
        } else {
            holder.tvMonthYear.visibility = View.GONE
        }

        holder.tvTitle.text = item.title ?: ""
        holder.tvData.text = item.getDataText()
        holder.tvChannel.text = item.canal ?: ""

        holder.btnArrow.setOnClickListener {
            mListener.onClickListener(item)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contentClick : View = itemView.findViewById(R.id.contentClick)
        val contentShimmer : ShimmerFrameLayout = itemView.findViewById(R.id.contentShimmer)
        val cardImage : CardViewImage = itemView.findViewById(R.id.cardImage)
        val tvMonthYear: TextView = itemView.findViewById(R.id.tvMonthYear)
        val tvTitle: TextView  = itemView.findViewById(R.id.tvTitle)
        val tvData: TextView = itemView.findViewById(R.id.tvData)
        val tvChannel: TextView = itemView.findViewById(R.id.tvChannel)
        val btnArrow: View = itemView.findViewById(R.id.btnArrow)
    }
}