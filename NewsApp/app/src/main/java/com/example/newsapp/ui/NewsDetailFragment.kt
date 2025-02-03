package com.example.newsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.model.News

class NewsDetailFragment : Fragment() {

    private val args: NewsDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_news_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsReport: News = args.selectedNews

        val imageView = view.findViewById<ImageView>(R.id.newsImageView)
        view.findViewById<TextView>(R.id.titleView).text = newsReport.title
        view.findViewById<TextView>(R.id.descriptionView).text = newsReport.description
        view.findViewById<TextView>(R.id.authorView).text = newsReport.author

        newsReport.image.let { imageUrl ->
            Glide.with(this@NewsDetailFragment)
                .load(imageUrl)
                .into(imageView)
        } ?: imageView.setImageResource(R.drawable.default_image)

    }

}