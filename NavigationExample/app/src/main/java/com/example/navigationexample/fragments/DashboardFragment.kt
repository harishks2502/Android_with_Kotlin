package com.example.navigationexample.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.navigationexample.R
import com.example.navigationexample.model.User

class DashboardFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = User(name = "Harish K S", userId = 2502)

        val title: String = view.findViewById<TextView>(R.id.titleText).text.toString()

        val settingsButton = view.findViewById<Button>(R.id.settingsButton)
        val profileButton = view.findViewById<Button>(R.id.profileButton)

        settingsButton.setOnClickListener {
            // Using Safe Args to pass the title
            val action =
                DashboardFragmentDirections.actionDashboardFragmentToSettingsFragment(title)
            findNavController().navigate(action)
        }

        profileButton.setOnClickListener {
            // Using Safe Args to pass the user
            val action =
                DashboardFragmentDirections.actionDashboardFragmentToProfileFragment(user)
            findNavController().navigate(action)
        }
    }

}