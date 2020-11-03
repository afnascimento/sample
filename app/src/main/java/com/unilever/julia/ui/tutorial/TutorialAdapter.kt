package com.unilever.julia.ui.tutorial

import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.unilever.julia.R
import com.unilever.julia.data.model.News
import com.unilever.julia.glide.GlideApp

class TutorialAdapter(val mActivity: Activity) : RecyclerView.Adapter<TutorialAdapter.ViewHolder>() {

    private var mDataSet : MutableList<News.Item> = arrayListOf()

    fun addItems(items : MutableList<News.Item>) {
        if (!items.isNullOrEmpty()) {
            mDataSet.clear()
            mDataSet.addAll(items)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tutorial_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mDataSet[position]

        holder.tvTitle.text = item.title
        holder.tvDescription.text = item.description

        GlideApp
            .with(mActivity)
            .load(item.image)
            .fitCenter()
            //.placeholder(R.drawable.ic_image_loading)
            //.error(R.drawable.ic_image_error)
            //.fallback(R.drawable.ic_image_error)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(object : DrawableImageViewTarget(holder.image) {

                override fun onLoadStarted(placeholder: Drawable?) {
                    holder.progress.visibility = View.VISIBLE
                    super.onLoadStarted(placeholder)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    holder.progress.visibility = View.GONE
                    super.onLoadFailed(errorDrawable)
                }

                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    holder.progress.visibility = View.GONE
                    super.onResourceReady(resource, transition)
                }
            })
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progress : View = itemView.findViewById(R.id.progress)
        val image : ImageView = itemView.findViewById(R.id.image)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvDescription: TextView  = itemView.findViewById(R.id.tvDescription)
    }
}