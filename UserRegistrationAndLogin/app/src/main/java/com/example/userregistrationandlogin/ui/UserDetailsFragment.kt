package com.example.userregistrationandlogin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.userregistrationandlogin.R
import com.example.userregistrationandlogin.databinding.FragmentUserDetailsBinding
import com.example.userregistrationandlogin.model.UsersModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserDetailsFragment : Fragment() {

    private var _binding: FragmentUserDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: UserDetailsFragmentArgs by navArgs()

    private lateinit var dbRefUsers: DatabaseReference
    private lateinit var dbRefTodos: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userDetails: UsersModel = args.userDetails

        updateUI(userDetails)

        binding.editDetailsButton.setOnClickListener {
            val action =
                UserDetailsFragmentDirections.actionUserDetailsFragmentToEditUserDetailsDialogFragment(
                    userDetails
                )
            findNavController().navigate(action)
        }

        binding.deleteAccountButton.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Delete Account")
                .setMessage("Are you sure you want to delete your account?")
                .setPositiveButton("Delete") { _, _ ->
                    dbRefUsers = FirebaseDatabase.getInstance().getReference("Users")
                    dbRefTodos = FirebaseDatabase.getInstance().getReference("Todos")

                    val sanitizedMailID = userDetails.mailID.toString().replace(".", ",")

                    dbRefUsers.child(sanitizedMailID).removeValue().addOnSuccessListener {
                        dbRefTodos.child(sanitizedMailID).removeValue().addOnSuccessListener {
                            Toast.makeText(requireContext(), "Account deleted successfully", Toast.LENGTH_SHORT)
                                .show()
                            findNavController().navigate(R.id.action_userDetailsFragment_to_loginFragment)
                        }
                    }.addOnFailureListener {
                        Toast.makeText(requireContext(), "Failed to delete user", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }

            val alert = builder.create()
            alert.show()
        }

    }

    private fun updateUI(userDetails: UsersModel) {
        binding.mailIDField.text = "Mail ID: ${userDetails.mailID}"
        binding.fullNameField.text = "Name: ${userDetails.fullName}"
        binding.usernameField.text = "Username: ${userDetails.username}"
        binding.passwordField.text = "Password: ${userDetails.password}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
