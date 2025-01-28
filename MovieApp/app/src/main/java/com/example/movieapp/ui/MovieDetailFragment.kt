package com.example.movieapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.movieapp.R

class MovieDetailFragment : Fragment() {

    private val args: MovieDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie = args.selectedMovie

        val imageView = view.findViewById<ImageView>(R.id.posterImageView)
        view.findViewById<TextView>(R.id.titleTextView).text = movie.title
        view.findViewById<TextView>(R.id.overviewTextView).text = movie.overview

        movie.poster_path?.let {
            val imageUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"
            Glide.with(this@MovieDetailFragment)
                .load(imageUrl)
                .into(imageView)
        } ?: run {
            imageView.setImageResource(R.drawable.default_image)
        }

    }

}