package com.example.imageviews.adapter

import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imageviews.R
import com.example.imageviews.model.Image


class ImageAdapter(private val images: List<Image>) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleView: TextView = itemView.findViewById(R.id.titleView)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = images[position]

//        val titleText = holder.itemView.context.getString(R.string.image_title, image.title)
//        holder.titleView.text = titleText

        val titleText = holder.itemView.context.getString(R.string.image_title, image.title)
        val spannableTitle = SpannableString(titleText)
        val start = titleText.indexOf("Title:")
        val end = start + "Title:".length
        spannableTitle.setSpan(
            StyleSpan(Typeface.BOLD),
            start,                      // Start index of "Title:"
            end,                        // End index of "Title:"
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        holder.titleView.text = spannableTitle

        Glide.with(holder.itemView.context)
            .load(image.thumbnailUrl)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(holder.imageView)

//        Picasso.get()
//            .load(image.url)
//            .placeholder(R.drawable.placeholder)
//            .error(R.drawable.placeholder)
//            .into(holder.imageView)
    }

    override fun getItemCount(): Int = images.size

}