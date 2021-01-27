package com.kn.appwallpaper.adapter

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.kn.appwallpaper.R
import com.kn.appwallpaper.model.Photo
import kotlinx.android.synthetic.main.photo_item.view.*

class TrendingImagesAdapter(
    var photos: ArrayList<Photo>,
    private val context: Context,
    private var clickListener: TrendingImagesAdapter.OnItemClickedListener
) : RecyclerView.Adapter<TrendingImagesAdapter.TrendingImagesViewHolder>() {
    class TrendingImagesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView = view.photo_prev
        private val progressBar = view.progressBar
        private val username = view.text_view_username
        fun bind(
            photo: Photo,
            context: Context,
            action: TrendingImagesAdapter.OnItemClickedListener
        ) {
            //load image into view
            Glide.with(context).asBitmap().load(photo.src.portrait)
                .listener(object : RequestListener<Bitmap> {
                    override fun onResourceReady(
                        resource: Bitmap?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                })
                .into(imageView)

            //seton click listener on image
            imageView.setOnClickListener {
                action.onTrendingImageCLicked(photo)
            }

            //set user name on photo
            username.text = photo.photographer
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrendingImagesAdapter.TrendingImagesViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.photo_item, parent, false)
        return TrendingImagesViewHolder(itemView)
    }

    override fun getItemCount() = photos.size

    override fun onBindViewHolder(holder: TrendingImagesViewHolder, position: Int) {
        holder.bind(photos[position], context, clickListener)
    }

    interface OnItemClickedListener {
        fun onTrendingImageCLicked(photo: Photo)
    }
}