package com.example.userregistrationandlogin.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.userregistrationandlogin.R
import com.example.userregistrationandlogin.databinding.FragmentAddTaskBinding
import com.example.userregistrationandlogin.databinding.FragmentHomeBinding
import com.example.userregistrationandlogin.model.TodosModel
import com.example.userregistrationandlogin.model.UsersModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.UUID


class AddTaskFragment : Fragment() {

    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!

    private val args: AddTaskFragmentArgs by navArgs()

    private lateinit var userDetails: UsersModel
    private lateinit var dbRef: DatabaseReference
    private lateinit var selectedStatus: String
    private lateinit var sanitizedMailID: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userDetails = args.userDetails

        sanitizedMailID = userDetails.mailID.toString().replace(".", ",")

        val statusOptions = resources.getStringArray(R.array.status_options)
        binding.statusSpinner.adapter = ArrayAdapter(
            requireContext(), R.layout.spinner_item, statusOptions
        ).apply {
            setDropDownViewResource(R.layout.spinner_dropdown_item)
        }
        binding.statusSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedStatus = statusOptions[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedStatus = "Pending"
            }
        }

        binding.addButton.setOnClickListener {
            val taskName = binding.taskNameField.text.toString()
            val taskDescription = binding.taskDescriptionField.text.toString()

            if (taskName.isNotBlank() && taskDescription.isNotBlank()) {
                addTaskToFirebase(taskName, taskDescription)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please enter task name and description",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun addTaskToFirebase(taskName: String, taskDescription: String) {

        val uniqueID = UUID.randomUUID().toString()

        val newTodo = TodosModel(uniqueID, taskName, taskDescription, selectedStatus)

        dbRef = FirebaseDatabase.getInstance().getReference("Todos")

        dbRef.child(sanitizedMailID).child(newTodo.id!!).setValue(newTodo).addOnSuccessListener {
            Toast.makeText(requireContext(), "Task Added Successfully", Toast.LENGTH_SHORT).show()

            val action = AddTaskFragmentDirections.actionAddTaskFragmentToHomeFragment(userDetails)
            findNavController().navigate(action)
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Failed to add task", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}