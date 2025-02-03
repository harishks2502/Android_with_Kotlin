package com.example.navigationexample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.navigationexample.R


class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = SettingsFragmentArgs.fromBundle(requireArguments())
        val title = args.title

        view.findViewById<TextView>(R.id.titleText).text = title

        val inputDeviceName = view.findViewById<EditText>(R.id.input_device_name)

        view.findViewById<View>(R.id.saveDeviceButton).setOnClickListener {
            val deviceName = inputDeviceName.text.toString().trim()

            if (deviceName.isNotBlank()) {
                Toast.makeText(context, "Device name saved: $deviceName", Toast.LENGTH_SHORT).show()
                inputDeviceName.text.clear()
            } else {
                Toast.makeText(context, "Please enter a device name", Toast.LENGTH_SHORT).show()
            }
        }

        val dashboardButton = view.findViewById<Button>(R.id.dashboardButton)

        dashboardButton.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_dashboardFragment)
        }

        // Add back navigation callback
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_settingsFragment_to_dashboardFragment)
        }

    }

}