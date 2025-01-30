package com.example.milestoneassessment02.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.milestoneassessment02.R
import com.example.milestoneassessment02.client.RetrofitClient
import com.example.milestoneassessment02.model.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class WeatherFragment : Fragment() {

    private lateinit var locationField: TextView
    private lateinit var temperatureField: TextView
    private lateinit var detailsViewButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cityNameField = view.findViewById<EditText>(R.id.cityNameField)
        val fetchButton = view.findViewById<Button>(R.id.fetchButton)

        locationField = view.findViewById(R.id.locationField)
        temperatureField = view.findViewById(R.id.temperatureField)
        detailsViewButton = view.findViewById(R.id.detailsViewButton)

        var city: String = "Bengaluru"
        fetchWeatherData(city)

        fetchButton.setOnClickListener {
            city = cityNameField.text.toString()
            if (city.isNotEmpty()) {
                fetchWeatherData(city)
                cityNameField.text.clear()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please enter a city name",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun fetchWeatherData(city: String) {
        RetrofitClient.getWeather(city).enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val weatherResponse = response.body()!!
                    locationField.text = "Location: ${weatherResponse.name}"

                    temperatureField.text =
                        "Temperature: %.2f degree Celcius".format(weatherResponse.main.temp - 273.15)

                    detailsViewButton.setOnClickListener {
                        val action =
                            WeatherFragmentDirections.actionWeatherFragmentToWeatherDetailFragment(
                                weatherResponse
                            )
                        findNavController().navigate(action)
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Error: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

}