package com.unilever.julia.ui.inovacao.detailV2.component

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.facebook.shimmer.ShimmerFrameLayout
import com.unilever.julia.components.R
import com.unilever.julia.components.innovations.IProduct
import com.unilever.julia.glide.GlideApp
import com.unilever.julia.utils.capitalizeAllText

class InnovationGridProducts<T : IProduct> : RelativeLayout {

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private lateinit var tvTitle : TextView //by bind<TextView>(R.id.tvTitle)
    private lateinit var gridView : GridView//by bind<GridView>(R.id.gridView)

    private lateinit var mAdapter : Adapter<T>

    private fun init(context: Context) {
        inflate(context, R.layout.innovation_v2_grid_products, this)
        tvTitle = findViewById(R.id.tvTitle)

        mAdapter = Adapter(context)

        gridView = findViewById(R.id.gridView)
        gridView.adapter = mAdapter
    }

    fun setTitle(text: String) {
        tvTitle.text = text.capitalizeAllText()
    }

    fun addItems(items : List<T>) {
        mAdapter.addItems(items)
        mAdapter.notifyDataSetChanged()
    }

    fun setListener(listener : Listener<T>) {
        mAdapter.setListener(listener)
    }

    interface Listener<T> {
        fun onClickItem(position: Int, products: List<T>)
    }

    class Adapter<T>(val mContext : Context) : BaseAdapter() {

        private val mData : MutableList<T> = arrayListOf()

        fun addItems(items : List<T>) {
            mData.clear()
            mData.addAll(items)
        }

        private var mListener : Listener<T>? = null

        fun setListener(listener : Listener<T>) {
            mListener = listener
        }

        @SuppressLint("SetTextI18n")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view : View
            if (convertView == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.innovation_item_grid, parent, false)
                view.tag = ViewHolder(view)
            } else {
                view = convertView
            }
            val holder : ViewHolder = view.tag as ViewHolder

            val item : IProduct = getItem(position) as IProduct

            GlideApp
                .with(mContext)
                .load(item.getProductImage())
                //.fitCenter()
                //.placeholder(com.unilever.julia.R.drawable.ic_image_loading)
                .error(com.unilever.julia.R.drawable.ic_image_error)
                .fallback(com.unilever.julia.R.drawable.ic_image_error)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(object : DrawableImageViewTarget(holder.image) {

                    override fun onLoadStarted(placeholder: Drawable?) {
                        holder.contentShimmer.visibility = View.VISIBLE
                        super.onLoadStarted(placeholder)
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        holder.contentShimmer.visibility = View.GONE
                        super.onLoadFailed(errorDrawable)
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        holder.contentShimmer.visibility = View.GONE
                        super.onResourceReady(resource, transition)
                    }
                })
            holder.textView.text = item.getProductDescription() + "\n\n"

            holder.contentClick.setOnClickListener {
                mListener?.onClickItem(position, mData)
            }

            return view
        }

        override fun getItem(position: Int): T {
            return mData[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getCount(): Int {
            return mData.size
        }

        class ViewHolder(view: View) {
            val contentShimmer : ShimmerFrameLayout = view.findViewById(R.id.shimmer)
            var contentClick = view.findViewById<View>(R.id.contentClick)
            var image = view.findViewById<ImageView>(R.id.image)
            var textView = view.findViewById<TextView>(R.id.textView)
        }
    }
}