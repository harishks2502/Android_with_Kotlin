package com.example.userdetailsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.userdetailsapp.R
import com.example.userdetailsapp.model.Todo

class TodoAdapter(private val todos: List<Todo>) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val completedStatusView: TextView = itemView.findViewById(R.id.completedStatusView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todos[position]
        holder.titleTextView.text = "Task Title: ${todo.title}"
        if (todo.completed === "true")
            holder.completedStatusView.text = "Task Status: Completed"
        else
            holder.completedStatusView.text = "Task Status: Not Completed"

    }

    override fun getItemCount() = todos.size
}