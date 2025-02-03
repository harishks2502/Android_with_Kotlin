package com.example.smarthomecontrollerapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.smarthomecontrollerapp.R
import com.example.smarthomecontrollerapp.databinding.FragmentLivingRoomBinding


class LivingRoomFragment : Fragment() {

    private var _binding: FragmentLivingRoomBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLivingRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val livingRoomLight = binding.switchLivingRoomLight
        val bedroomLight = binding.switchBedroomLight
        val kitchenLight = binding.switchKitchenLight
        val thermostatText = binding.thermostatValue
        val thermostatSlider = binding.thermostatSlider
        val saveSettings = binding.saveSettingButton

        thermostatSlider.addOnChangeListener { slider, value, fromUser ->
            thermostatText.text = String.format("%.2f°C", value)
        }

        saveSettings.setOnClickListener {
            Toast.makeText(requireContext(), "Options Saved", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}