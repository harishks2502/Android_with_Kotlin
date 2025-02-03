package com.example.userdetailsapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.userdetailsapp.R
import com.example.userdetailsapp.adapter.TodoAdapter
import com.example.userdetailsapp.client.RetrofitClient
import com.example.userdetailsapp.model.Todo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity2 : AppCompatActivity() {
    private lateinit var todosRecyclerView: RecyclerView
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        todosRecyclerView = findViewById(R.id.todosRecyclerView)
        todosRecyclerView.layoutManager = LinearLayoutManager(this)

        fetchTodos()
    }

    private fun fetchTodos() {
        //select * from users_baseurl
        RetrofitClient.instance.getTodos().enqueue(object : Callback<List<Todo>> {
            override fun onResponse(call: Call<List<Todo>>, response: Response<List<Todo>>) {
                if (response.isSuccessful) {
                    response.body()?.let { todos ->
                        todoAdapter = TodoAdapter(todos)
                        todosRecyclerView.adapter = todoAdapter
                    }
                } else {
                    Toast.makeText(
                        this@MainActivity2,
                        "Error: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Todo>>, t: Throwable) {
                Toast.makeText(this@MainActivity2, "Failure: ${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

}