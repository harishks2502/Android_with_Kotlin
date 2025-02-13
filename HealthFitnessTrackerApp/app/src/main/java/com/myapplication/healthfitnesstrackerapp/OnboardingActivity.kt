package com.myapplication.healthfitnesstrackerapp



import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager


class OnboardingActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager
    private lateinit var skipButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        viewPager = findViewById(R.id.viewPager)
        skipButton = findViewById(R.id.btnSkip)

        // Provide onboarding slides data
        val slides = listOf(
            "Welcome to Fitness Tracker!",
            "Track your steps and calories burned.",
            "Sync with your wearable device easily."
        )

        // Correctly pass the slide data to OnboardingAdapter
        val adapter = OnboardingAdapter(this, slides)
        viewPager.adapter = adapter

        skipButton.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }
    }
}
