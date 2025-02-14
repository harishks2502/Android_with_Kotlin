package com.example.sampleapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.sampleapplication.model.User

class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title: String = view.findViewById<TextView>(R.id.titleText).text.toString()
        val user = User(name = "Harish K S", userId = 2502, title)

        val settingsButton = view.findViewById<Button>(R.id.settingsButton)

        settingsButton.setOnClickListener {
            val action =
                DashboardFragmentDirections.actionDashboardFragmentToSettingsFragment(user)
            findNavController().navigate(action)
        }

    }

}