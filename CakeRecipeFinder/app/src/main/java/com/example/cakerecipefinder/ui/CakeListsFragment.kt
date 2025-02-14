package com.example.cakerecipefinder.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cakerecipefinder.adapter.CakeAdapter
import com.example.cakerecipefinder.client.RetrofitClient
import com.example.cakerecipefinder.databinding.FragmentCakeListsBinding
import com.example.cakerecipefinder.model.CakeRecipe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CakeListsFragment : Fragment() {

    private var _binding: FragmentCakeListsBinding? = null
    private val binding get() = _binding!!

    private lateinit var cakelistsRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCakeListsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cakelistsRecyclerView = binding.cakelistsRecyclerView
        cakelistsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        fetchCakeRecipeLists()
    }

    private fun fetchCakeRecipeLists() {
        RetrofitClient.getCakeRecipes()
            .enqueue(object : Callback<CakeRecipe> {
                override fun onResponse(call: Call<CakeRecipe>, response: Response<CakeRecipe>) {
                    if (response.isSuccessful && response.body() != null) {
                        val meals = response.body()!!.meals
                        cakelistsRecyclerView.adapter = CakeAdapter(meals) { selectedCakeList ->
                            Log.d("CakeListsFragment", "Selected cake list: $selectedCakeList")
                            val action = CakeListsFragmentDirections.actionCakeListsFragmentToCakeRecipeFragment(selectedCakeList)
                            findNavController().navigate(action)
                        }
                    } else {
                        Toast.makeText(requireContext(), "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<CakeRecipe>, t: Throwable) {
                    Toast.makeText(requireContext(), "Failed to fetch: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
