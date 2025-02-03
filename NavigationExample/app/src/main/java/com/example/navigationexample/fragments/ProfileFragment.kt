package com.example.navigationexample.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.example.navigationexample.R


class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve the arguments using Safe Args
        val args = ProfileFragmentArgs.fromBundle(requireArguments())
        val user = args.user

        view.findViewById<TextView>(R.id.userNameTextView).text = user.name
        view.findViewById<TextView>(R.id.userIdTextView).text = user.userId.toString()

        val inputUsername = view.findViewById<EditText>(R.id.input_username)

        view.findViewById<View>(R.id.saveUsernameButton).setOnClickListener {
            val username = inputUsername.text.toString().trim()

            if (username.isNotBlank()) {
                Toast.makeText(context, "Username saved: $username", Toast.LENGTH_SHORT).show()
                inputUsername.text.clear()
            } else {
                Toast.makeText(context, "Please enter a username", Toast.LENGTH_SHORT).show()
            }
        }

        val dashboardButton = view.findViewById<Button>(R.id.dashboardButton)

        dashboardButton.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_dashboardFragment)
        }

        // Add back navigation callback
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_profileFragment_to_dashboardFragment)
        }

    }

}