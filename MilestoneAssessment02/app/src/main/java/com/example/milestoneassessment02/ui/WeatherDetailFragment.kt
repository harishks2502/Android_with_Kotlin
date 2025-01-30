package com.example.milestoneassessment02.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.example.milestoneassessment02.R
import com.example.milestoneassessment02.model.WeatherResponse


class WeatherDetailFragment : Fragment() {

    private val args: WeatherDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_weather_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weatherReport: WeatherResponse = args.weatherReport

        view.findViewById<TextView>(R.id.locationField).text = "Location: ${weatherReport.name}"

        view.findViewById<TextView>(R.id.temperatureField).text =
            "Temperature: %.2f degree Celcius".format(weatherReport.main.temp - 273.15)

        view.findViewById<TextView>(R.id.pressureField).text =
            "Pressure: ${weatherReport.main.pressure} hPa"

        view.findViewById<TextView>(R.id.humidityField).text =
            "Humidity: ${weatherReport.main.humidity}%"

        view.findViewById<TextView>(R.id.windSpeedField).text =
            "Wind Speed: ${weatherReport.wind.speed}%"
    }

}