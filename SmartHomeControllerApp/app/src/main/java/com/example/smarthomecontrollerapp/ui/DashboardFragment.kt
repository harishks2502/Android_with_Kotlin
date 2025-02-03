package com.example.smarthomecontrollerapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.smarthomecontrollerapp.R
import com.example.smarthomecontrollerapp.client.RetrofitClient
import com.example.smarthomecontrollerapp.databinding.FragmentDashboardBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userProfile = binding.userProfile
        val livingRoom = binding.livingRoomLayout

        userProfile.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_userProfileFragment)
        }

        livingRoom.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_livingRoomFragment)
        }


        fetchWeatherData()

    }

    private fun fetchWeatherData() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val weatherResponse = RetrofitClient.getWeather()

                binding.temperatureField.text = "% .2f°C".format(weatherResponse.main.temp - 273.15)
                binding.locationField.text = "Location: ${weatherResponse.name}"

            } catch (e: Exception) {
                Snackbar.make(requireView(), "Error: ${e.message}", Snackbar.LENGTH_SHORT).show()
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}