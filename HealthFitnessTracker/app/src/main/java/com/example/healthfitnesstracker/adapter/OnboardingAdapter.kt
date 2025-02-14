package com.example.healthfitnesstracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.healthfitnesstracker.R

class OnboardingAdapter(private val context: Context, private val slides: List<String>) :
    PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.item_onboarding, container, false)
        val textView: TextView = view.findViewById(R.id.textOnboarding)
        textView.text = slides[position]
        container.addView(view)
        return view
    }

    override fun getCount(): Int = slides.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}