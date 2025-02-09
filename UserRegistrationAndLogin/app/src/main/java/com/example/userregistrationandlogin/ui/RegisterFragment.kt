package com.example.userregistrationandlogin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.userregistrationandlogin.R
import com.example.userregistrationandlogin.databinding.FragmentRegisterBinding
import com.example.userregistrationandlogin.model.UsersModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerButton.setOnClickListener {
            val mailID = binding.mailIDField.text.toString().trim()
            val fullName = binding.fullNameField.text.toString().trim()
            val username = binding.usernameField.text.toString().trim()
            val password = binding.passwordField.text.toString().trim()

            when {
                mailID.isEmpty() -> {
                    binding.mailIDField.error = "Please enter the mail ID"
                    binding.mailIDField.requestFocus()
                }
                !android.util.Patterns.EMAIL_ADDRESS.matcher(mailID).matches() -> {
                    binding.mailIDField.error = "Invalid email format"
                    binding.mailIDField.requestFocus()
                }
                fullName.isEmpty() -> {
                    binding.fullNameField.error = "Please enter your full name"
                    binding.fullNameField.requestFocus()
                }
                username.isEmpty() -> {
                    binding.usernameField.error = "Please enter the username"
                    binding.usernameField.requestFocus()
                }
                password.length < 6 -> {
                    binding.passwordField.error = "Password must be at least 6 characters"
                    binding.passwordField.requestFocus()
                }
                else -> {
                    val sanitizedMailID = mailID.replace(".", ",")
                    registerUser(sanitizedMailID, fullName, username, password)
                }
            }
        }
    }

    private fun registerUser(mailID: String, fullName: String, username: String, password: String) {
        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        dbRef.child(mailID).get().addOnSuccessListener {
            if (it.exists()) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Error")
                    .setMessage("User already exists, try with a different mail ID.")
                    .setPositiveButton("Ok", null)
                    .show()
            } else {
                val user = UsersModel(mailID, fullName, username, password)
                dbRef.child(mailID).setValue(user).addOnSuccessListener {
                    AlertDialog.Builder(requireContext()).setTitle("Message")
                        .setMessage("User Registered Successfully")
                        .setPositiveButton("Ok") { _, _ ->
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        }.show()
                }.addOnFailureListener {
                    Toast.makeText(requireContext(), "Failed to add user, please try again.", Toast.LENGTH_SHORT).show()
                }
            }
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Error checking database, please try again.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
