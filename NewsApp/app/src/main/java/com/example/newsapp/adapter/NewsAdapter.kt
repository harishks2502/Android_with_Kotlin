package com.example.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.model.News

class NewsAdapter(
    private val news: List<News>,
    private val onClick: (News) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleView = itemView.findViewById<TextView>(R.id.titleView)
        private val viewButton = itemView.findViewById<Button>(R.id.viewDetailsButton)

        fun bind(news: News) {
            titleView.text = news.title
            viewButton.setOnClickListener { onClick(news) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.NewsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.individual_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsAdapter.NewsViewHolder, position: Int) {
        val newsResult = news[position]
        holder.bind(newsResult)
    }

    override fun getItemCount(): Int = news.size

}