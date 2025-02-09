package com.example.userregistrationandlogin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.userregistrationandlogin.R
import com.example.userregistrationandlogin.databinding.FragmentUpdateTaskBinding
import com.example.userregistrationandlogin.model.TodosModel
import com.example.userregistrationandlogin.model.UsersModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class UpdateTaskFragment : Fragment() {

    private var _binding: FragmentUpdateTaskBinding? = null
    private val binding get() = _binding!!

    private val args: UpdateTaskFragmentArgs by navArgs()

    private lateinit var userDetails: UsersModel
    private lateinit var dbRef: DatabaseReference
    private lateinit var selectedStatus: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val taskDetails = args.todoDetails
        userDetails = args.userDetails

        binding.taskNameField.setText(taskDetails.taskName)
        binding.taskDescriptionField.setText(taskDetails.taskDescription)

        val statusOptions = resources.getStringArray(R.array.status_options)
        binding.statusSpinner.adapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item,
            statusOptions
        ).apply {
            setDropDownViewResource(R.layout.spinner_dropdown_item)
        }
        binding.statusSpinner.setSelection(statusOptions.indexOf(taskDetails.taskStatus))
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
                selectedStatus = taskDetails.taskStatus!!
            }
        }

        binding.updateButton.setOnClickListener {
            val taskName = binding.taskNameField.text.toString()
            val taskDescription = binding.taskDescriptionField.text.toString()
            updateTask(taskDetails.id!!, userDetails.mailID!!, taskName, taskDescription)
        }

    }

    private fun updateTask(
        taskId: String,
        mailID: String,
        taskName: String,
        taskDescription: String
    ) {
        val sanitizedMailID = mailID.replace(".", ",")

        val updatedTask = mapOf(
            "taskDescription" to taskDescription,
            "taskName" to taskName,
            "taskStatus" to selectedStatus
        )

        dbRef = FirebaseDatabase.getInstance().getReference("Todos").child(sanitizedMailID)
        dbRef.child(taskId).updateChildren(updatedTask).addOnSuccessListener {
            Toast.makeText(requireContext(), "Task Updated Successfully", Toast.LENGTH_SHORT).show()
            val action =
                UpdateTaskFragmentDirections.actionUpdateTaskFragmentToHomeFragment(userDetails)
            findNavController().navigate(action)
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Failed to update task", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}