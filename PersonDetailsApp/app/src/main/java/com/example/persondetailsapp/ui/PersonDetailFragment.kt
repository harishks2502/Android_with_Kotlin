package com.example.persondetailsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.persondetailsapp.R

class PersonDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_person_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = PersonDetailFragmentArgs.fromBundle(requireArguments())
        val person = args.selectedPerson

        val imageView = view.findViewById<ImageView>(R.id.personImageView)
        view.findViewById<TextView>(R.id.personNameTextView).text = person.name

        person.image?.let {
            val imageUrl = it.original
            Glide.with(this@PersonDetailFragment)
                .load(imageUrl)
                .into(imageView)
        } ?: run {
            imageView.setImageResource(R.drawable.default_person_image)
        }
    }

}