package com.example.newsapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.client.RetrofitClient
import com.example.newsapp.model.NewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsListFragment : Fragment() {

    private lateinit var newsRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsRecyclerView = view.findViewById(R.id.newsRecyclerView)
        newsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        fetchNews()
    }

    private fun fetchNews() {
        RetrofitClient.getNewsResponse()
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    Log.d("NewsListFragment", "onResponse: $response")

                    if (response.isSuccessful && response.body() != null) {
                        val news = response.body()!!.news
                        if (news.isEmpty()) {
                            Toast.makeText(
                                requireContext(),
                                "No news available.",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            newsRecyclerView.adapter = NewsAdapter(news) { selectedNews ->
                                val action =
                                    NewsListFragmentDirections.actionNewsListFragmentToNewsDetailFragment(
                                        selectedNews
                                    )
                                findNavController().navigate(action)
                            }
                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e("NewsListFragment", "Error: ${response.code()}, Body: $errorBody")

                        Toast.makeText(
                            requireContext(),
                            "Error fetching news: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    Toast.makeText(
                        requireContext(),
                        "Failed to fetch news: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

}