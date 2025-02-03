package com.example.userregistrationandlogin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.userregistrationandlogin.databinding.FragmentEditUserDetailsDialogBinding
import com.example.userregistrationandlogin.model.UsersModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class EditUserDetailsDialogFragment : DialogFragment() {

    private var _binding: FragmentEditUserDetailsDialogBinding? = null
    private val binding get() = _binding!!

    private val args: EditUserDetailsDialogFragmentArgs by navArgs()

    private lateinit var dbRef: DatabaseReference

    private lateinit var mailID: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditUserDetailsDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userDetails: UsersModel = args.userDetails

        mailID = userDetails.mailID.toString()

        binding.mailIDField.text = mailID

        binding.editFullNameField.setText(userDetails.fullName)
        binding.editUsernameField.setText(userDetails.username)
        binding.editPasswordField.setText(userDetails.password)

        binding.saveButton.setOnClickListener {
            val fullName = binding.editFullNameField.text.toString()
            val username = binding.editUsernameField.text.toString()
            val password = binding.editPasswordField.text.toString()

            when {
                fullName.isEmpty() -> {
                    binding.editFullNameField.error = "Please enter your full name"
                    binding.editFullNameField.requestFocus()
                }
                username.isEmpty() -> {
                    binding.editUsernameField.error = "Please enter the username"
                    binding.editUsernameField.requestFocus()
                }
                password.isEmpty() -> {
                    binding.editPasswordField.error = "Please enter the password"
                    binding.editPasswordField.requestFocus()
                }
                else -> {
                    updateUserDetails(fullName, username, password)
                }
            }

        }

    }

    private fun updateUserDetails(
        fullName: String, username: String, password: String
    ) {
        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        val user = UsersModel(mailID, fullName, username, password)

        val sanitizedMailID = mailID.replace(".", ",")
        dbRef.child(sanitizedMailID).setValue(user).addOnSuccessListener {
            Toast.makeText(requireContext(), "User details updated successfully", Toast.LENGTH_SHORT)
                .show()
            val action =
                EditUserDetailsDialogFragmentDirections.actionEditUserDetailsDialogFragmentToUserDetailsFragment(
                    user
                )
            findNavController().navigate(action)
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Failed to update user", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}