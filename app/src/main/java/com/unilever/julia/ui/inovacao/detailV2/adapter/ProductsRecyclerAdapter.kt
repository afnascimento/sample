package com.unilever.julia.ui.inovacao.detailV2.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.facebook.shimmer.ShimmerFrameLayout
import com.unilever.julia.R
import com.unilever.julia.data.model.inovacaoDetalhe.Produto
import com.unilever.julia.glide.GlideApp
import com.unilever.julia.utils.setAbreviateText

class ProductsRecyclerAdapter(val mFragment: Fragment,
                              val mDrawable : Drawable,
                              val isTitleEllipsize : Boolean) : RecyclerView.Adapter<ProductsRecyclerAdapter.ViewHolder>() {

    interface Listener {
        fun onClickItem(position : Int, products: MutableList<Produto>)
    }

    private var mListener : Listener? = null

    fun setListener(listener : Listener) {
        mListener = listener
    }

    private var mDataSet : MutableList<Produto> = arrayListOf()

    fun addItems(items : MutableList<Produto>?) {
        if (!items.isNullOrEmpty()) {
            mDataSet.clear()
            mDataSet.addAll(items)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.innovation_item_rcview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    private var imageVisible = true

    fun setImageVisible(visible : Boolean) {
        imageVisible = visible
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mDataSet[position]

        holder.contentClick.setOnClickListener {
            mListener?.onClickItem(position, mDataSet)
        }

        holder.tvTitle.text = item.title ?: ""
        if (isTitleEllipsize) {
            holder.tvTitle.setAbreviateText(1)
        }

        holder.tvDescription.text = item.description ?: ""

        if (!imageVisible) {
            holder.contentImage.visibility = View.GONE
            return
        }
        holder.contentImage.visibility = View.VISIBLE

        if (item.isErrorRenderer) {
            holder.image.setImageDrawable(mDrawable)
            return
        }

        GlideApp
            .with(mFragment)
            .load(item.image)
            .fitCenter()
            .placeholder(R.drawable.ic_image_loading)
            //dynamic url on error
            //.error(
            //    GlideApp
            //        .with(mFragment)
            //        .load("https://juliajobfunction.blob.core.windows.net/innovations/Marcas/Unilever_Logo_200x200_Omo.jpg")
            //)
            .error(mDrawable)
            .fallback(mDrawable)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(object : DrawableImageViewTarget(holder.image) {

                override fun onLoadStarted(placeholder: Drawable?) {
                    holder.contentShimmer.startShimmer()
                    super.onLoadStarted(placeholder)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    item.isErrorRenderer = true
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
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contentClick : View = itemView.findViewById(R.id.contentClick)
        val contentImage : View = itemView.findViewById(R.id.contentImage)
        val contentShimmer : ShimmerFrameLayout = itemView.findViewById(R.id.shimmerLoading)
        val image : ImageView = itemView.findViewById(R.id.image)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvDescription: TextView  = itemView.findViewById(R.id.tvDescription)
    }
}