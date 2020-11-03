package com.unilever.julia.ui.adapter

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.pnikosis.materialishprogress.ProgressWheel
import com.unilever.julia.R
import com.unilever.julia.components.innovations.InnovationsMidias
import com.unilever.julia.data.model.notificacao.Attached
import com.unilever.julia.data.model.notificacao.Type
import com.unilever.julia.data.model.notificacao.holder.AttachedViewType
import com.unilever.julia.data.model.notificacao.holder.HolderContentTop
import com.unilever.julia.glide.GlideApp
import com.unilever.julia.utils.ParsePatternsEnums
import com.unilever.julia.utils.RedirectIntents
import com.unilever.julia.utils.parseString

class NotificationDetailAdapter(var mListener : Listener) : RecyclerView.Adapter<NotificationDetailAdapter.ViewHolder>() {

    interface Listener {
        //fun onItemClick(item: Notification)
    }

    private var mDataSet: MutableList<AttachedViewType> = arrayListOf()

    fun addItems(items: List<AttachedViewType>) {
        mDataSet.clear()
        mDataSet.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return mDataSet[position].getViewType().ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (Type.values()[viewType]) {
            Type.CONTENT_TOP -> ContentTopViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.notificacao_item_top, parent, false))

            Type.IMAGE -> ImageViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.notificacao_item_image_layout, parent, false))

            Type.VIDEO -> VideoViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.notificacao_item_video_layout, parent, false))

            Type.PDF -> PDFViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.notificacao_item_pdf_layout, parent, false))

            Type.LINK_EXTERNO -> LinkExternoViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.notificacao_item_link_layout, parent, false))

            Type.LINK_INTERNO -> LinkInternoViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.notificacao_item_link_layout, parent, false))

            else -> ContentTopViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.notificacao_item_top, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mListener, mDataSet[position])
    }

    abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(listener : Listener, model: AttachedViewType)
    }

    class ContentTopViewHolder(itemView: View) : ViewHolder(itemView) {

        private val tvDate = itemView.findViewById<TextView>(R.id.tvDetailNotificationDate)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvDetailNotificationTitle)
        private val tvDescription = itemView.findViewById<TextView>(R.id.tvDetailNotificationDescription)

        override fun bind(listener : Listener, model: AttachedViewType) {
            val item : HolderContentTop = model as HolderContentTop
            tvDate.text = item.date?.parseString(ParsePatternsEnums.DDMMYYYY) ?: ""
            tvTitle.text = item.title
            tvDescription.text = item.description
        }
    }

    class ImageViewHolder(itemView: View) : ViewHolder(itemView) {

        private val ivBanner = itemView.findViewById<ImageView>(R.id.ivBanner)
        private val contentLoad = itemView.findViewById<View>(R.id.contentLoad)
        private val progress = itemView.findViewById<ProgressWheel>(R.id.progress)

        override fun bind(listener : Listener, model: AttachedViewType) {
            val item : Attached = model as Attached

            GlideApp
                .with(itemView.context)
                .load(item.url)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(object : DrawableImageViewTarget(ivBanner) {

                    override fun onLoadStarted(placeholder: Drawable?) {
                        contentLoad.visibility = View.VISIBLE
                        super.onLoadStarted(placeholder)
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        progress.visibility = View.GONE
                        super.onLoadFailed(errorDrawable)
                    }

                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        contentLoad.visibility = View.GONE
                        super.onResourceReady(resource, transition)
                    }
                })
        }
    }

    class VideoViewHolder(itemView: View) : ViewHolder(itemView) {

        private val midias = itemView.findViewById<InnovationsMidias>(R.id.midias)

        override fun bind(listener : Listener, model: AttachedViewType) {
            val context  = itemView.context

            val item: Attached = model as Attached

            midias.setTitle(context.getString(R.string.midias))
            midias.addMidias(
                arrayListOf(InnovationsMidias.Item("", item.label ?: "", item.url ?: ""))
            )
            midias.setListener(object : InnovationsMidias.Listener {
                override fun onPlayerClick(intent: Intent) {
                    ContextCompat.startActivity(context, intent, null)
                }
            })
        }
    }

    class PDFViewHolder(itemView: View) : ViewHolder(itemView) {

        private val btnDownloadPDF = itemView.findViewById<View>(R.id.btnDownloadPDF)

        override fun bind(listener: Listener, model: AttachedViewType) {
            val item: Attached = model as Attached

            btnDownloadPDF.setOnClickListener {
                ContextCompat.startActivity(
                    itemView.context,
                    Intent(Intent.ACTION_VIEW, Uri.parse(item.url)),
                    null
                )
            }
        }
    }

    class LinkExternoViewHolder(itemView: View) : ViewHolder(itemView) {

        private val btnLink : Button = itemView.findViewById(R.id.btnLink)

        override fun bind(listener: Listener, model: AttachedViewType) {
            val item: Attached = model as Attached

            btnLink.text = item.label ?: "LINK ESTERNO"
            btnLink.setOnClickListener {
                ContextCompat.startActivity(
                    itemView.context,
                    Intent(Intent.ACTION_VIEW, Uri.parse(item.url)),
                    null
                )
            }
        }
    }

    class LinkInternoViewHolder(itemView: View) : ViewHolder(itemView) {

        private val btnLink : Button = itemView.findViewById(R.id.btnLink)

        override fun bind(listener: Listener, model: AttachedViewType) {
            val item: Attached = model as Attached

            btnLink.text = item.label ?: "LINK INTERNO"
            btnLink.setOnClickListener {
                RedirectIntents.backToChatMainActivity(itemView.context as Activity, item.url ?: "oi")
            }
        }
    }

}