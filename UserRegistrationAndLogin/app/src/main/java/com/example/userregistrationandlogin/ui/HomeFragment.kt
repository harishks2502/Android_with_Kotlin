package com.example.userregistrationandlogin.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.userregistrationandlogin.adapter.TodoAdapter
import com.example.userregistrationandlogin.databinding.FragmentHomeBinding
import com.example.userregistrationandlogin.model.TodosModel
import com.example.userregistrationandlogin.model.UsersModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val args: HomeFragmentArgs by navArgs()

    private lateinit var todosRecyclerView: RecyclerView
    private lateinit var dbRef: DatabaseReference
    private lateinit var todoAdapter: TodoAdapter
    private val todos = mutableListOf<TodosModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userDetails: UsersModel = args.userDetails


        binding.fullNameField.text = "Welcome ${userDetails.fullName}"

        binding.userDetailsButton.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToUserDetailsFragment(userDetails)
            findNavController().navigate(action)
        }

        binding.addTaskButton.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddTaskFragment(userDetails)
            findNavController().navigate(action)
        }

        todosRecyclerView = binding.todosRecyclerView
        todosRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        todoAdapter = TodoAdapter(todos,
            onDelete = { todoID ->
                deleteTodo(todoID)
            },
            onUpdate = { todo ->
                val action = HomeFragmentDirections.actionHomeFragmentToUpdateTaskFragment(todo, userDetails)
                findNavController().navigate(action)
            }
        )

        todosRecyclerView.adapter = todoAdapter

        fetchTodos(userDetails.mailID!!)
    }

    private fun fetchTodos(mailID: String) {
        val sanitizedMailID = mailID.replace(".", ",")

        dbRef = FirebaseDatabase.getInstance().getReference("Todos")

        dbRef.child(sanitizedMailID).addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                todos.clear()
                snapshot.children.forEach { childSnapshot ->
                    val todo = childSnapshot.getValue(TodosModel::class.java)
                    todo?.let {
                        todos.add(it)
                    }
                }
                todoAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    requireContext(),
                    "Error fetching todos: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })

    }

    private fun deleteTodo(todoID: String) {
        val sanitizedMailID = args.userDetails.mailID!!.replace(".", ",")

        dbRef.child(sanitizedMailID).child(todoID).removeValue().addOnSuccessListener {
            Toast.makeText(requireContext(), "Task deleted successfully", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener { error ->
            Toast.makeText(requireContext(), "Failed to delete task: ${error.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}