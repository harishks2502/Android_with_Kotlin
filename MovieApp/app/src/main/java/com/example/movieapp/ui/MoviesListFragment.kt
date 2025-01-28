package com.example.movieapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.adapter.MovieAdapter
import com.example.movieapp.client.RetrofitClient
import com.example.movieapp.model.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesListFragment : Fragment() {

    private lateinit var moviesRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesRecyclerView = view.findViewById(R.id.moviesRecyclerView)
        moviesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        fetchMovies()
    }

    private fun fetchMovies() {
        RetrofitClient.getPopularMovies()
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val movies = response.body()!!.results
                        moviesRecyclerView.adapter = MovieAdapter(movies) { selectedMovie ->
                            val action =
                                MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailFragment(
                                    selectedMovie
                                )
                            findNavController().navigate(action)
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Error: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    Toast.makeText(
                        requireContext(),
                        "Failed to fetch movies: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
    }

}
