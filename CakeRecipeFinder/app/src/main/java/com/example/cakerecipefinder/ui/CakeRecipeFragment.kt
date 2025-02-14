package com.example.cakerecipefinder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.cakerecipefinder.databinding.FragmentCakeRecipeBinding

class CakeRecipeFragment : Fragment() {

    private var _binding: FragmentCakeRecipeBinding? = null
    private val binding get() = _binding!!

    private val args: CakeRecipeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCakeRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cake = args.selectedCakeList

        cake.strMealThumb.let {
            val imageURl = cake.strMealThumb
            Glide.with(this@CakeRecipeFragment)
                .load(imageURl)
                .into(binding.cakeImage)
        }

        binding.cakeTitle.text = "Title: ${cake.strMeal}"
        binding.cakeCategory.text = "Category: ${cake.strCategory}"
        binding.cakeArea.text = "Area: ${cake.strArea}"
        binding.cakeInstructions.text = "Instructions: ${cake.strInstructions}"
        binding.cakeTags.text = "Tags: ${cake.strTags}"
        binding.cakeReference.text = "YouTube Reference: ${cake.strYoutube}"
        binding.cakeSource.text = "Source: ${cake.strSource}"
    }

}