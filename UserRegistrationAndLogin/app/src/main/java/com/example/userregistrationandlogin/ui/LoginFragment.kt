package com.example.userregistrationandlogin.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.userregistrationandlogin.R
import com.example.userregistrationandlogin.databinding.FragmentLoginBinding
import com.example.userregistrationandlogin.model.UsersModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        binding.loginButton.setOnClickListener {
            val mailID = binding.mailIDField.text.toString().trim()
            val password = binding.passwordField.text.toString().trim()

            when {
                mailID.isEmpty() -> {
                    binding.mailIDField.error = "Please enter the mail ID"
                    binding.mailIDField.requestFocus()
                }

                password.isEmpty() -> {
                    binding.passwordField.error = "Please enter the password"
                    binding.passwordField.requestFocus()
                }

                else -> {
                    val sanitizedMailID = mailID.replace(".", ",")
                    loginUser(sanitizedMailID, password)
                }
            }
        }

        binding.registerField.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun loginUser(mailID: String, password: String) {
        dbRef.child(mailID).get().addOnSuccessListener {
            if (it.exists()) {
                val dbMailID = it.child("mailID").value.toString().replace(",", ".")
                val dbFullName = it.child("fullName").value.toString()
                val dbUsername = it.child("username").value.toString()
                val dbPassword = it.child("password").value.toString()

                if (password == dbPassword) {
                    val user = UsersModel(dbMailID, dbFullName, dbUsername, dbPassword)
                    val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment(user)
                    findNavController().navigate(action)
                } else {
                    Toast.makeText(requireContext(), "Invalid Credentials", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                AlertDialog.Builder(requireContext())
                    .setTitle("Error")
                    .setMessage("User not found")
                    .setPositiveButton("Ok", null)
                    .show()
            }
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Error checking database", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
