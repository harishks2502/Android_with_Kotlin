package com.example.imageviews.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imageviews.R
import com.example.imageviews.adapter.ImageAdapter
import com.example.imageviews.client.RetrofitClient
import com.example.imageviews.model.Image
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var imagesRecyclerView: RecyclerView
    private lateinit var imageAdapter: ImageAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imagesRecyclerView = findViewById(R.id.imagesRecyclerView)
        imagesRecyclerView.layoutManager = LinearLayoutManager(this)

        fetchImages()
    }

    private fun fetchImages() {
        RetrofitClient.instance.getPhotos().enqueue(object : Callback<List<Image>> {
            override fun onResponse(call: Call<List<Image>>, response: Response<List<Image>>) {
                if (response.isSuccessful) {
                    response.body()?.let { images ->
                        imageAdapter = ImageAdapter(images)
                        imagesRecyclerView.adapter = imageAdapter
                    }
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Error: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Image>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Failure: ${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

}