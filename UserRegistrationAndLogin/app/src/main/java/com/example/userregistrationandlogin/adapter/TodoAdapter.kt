package com.example.userregistrationandlogin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.userregistrationandlogin.R
import com.example.userregistrationandlogin.model.TodosModel

class TodoAdapter(
    private val todos: List<TodosModel>,
    private val onUpdate: (TodosModel) -> Unit,
    private val onDelete: (String) -> Unit
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val taskName = itemView.findViewById<TextView>(R.id.taskNameField)
        private val taskDescription = itemView.findViewById<TextView>(R.id.taskDescriptioField)
        private val taskStatus = itemView.findViewById<TextView>(R.id.taskStatusField)
        private val updateButton = itemView.findViewById<Button>(R.id.updateTaskButton)
        private val deleteButton = itemView.findViewById<Button>(R.id.deleteTaskButton)


        fun bind(todo: TodosModel) {
            taskName.text = todo.taskName
            taskDescription.text = todo.taskDescription
            taskStatus.text = todo.taskStatus

            updateButton.setOnClickListener {
                onUpdate(todo)
            }

            deleteButton.setOnClickListener {
                onDelete(todo.id!!)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.individual_task, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoAdapter.TodoViewHolder, position: Int) {
        val todo = todos[position]
        holder.bind(todo)
    }

    override fun getItemCount(): Int = todos.size

}