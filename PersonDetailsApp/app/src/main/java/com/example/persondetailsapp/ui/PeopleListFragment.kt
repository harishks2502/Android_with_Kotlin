package com.example.persondetailsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.persondetailsapp.R
import com.example.persondetailsapp.adapter.PersonAdapter
import com.example.persondetailsapp.client.RetrofitClient
import com.example.persondetailsapp.model.PeopleResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PeopleListFragment : Fragment() {

    private lateinit var peopleRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_people_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        peopleRecyclerView = view.findViewById(R.id.peopleRecyclerView)
        peopleRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        fetchPeople()
    }

    private fun fetchPeople() {
        RetrofitClient.searchPeople("lauren")
            .enqueue(object : Callback<List<PeopleResponse>> {
            override fun onResponse(
                call: Call<List<PeopleResponse>>,
                response: Response<List<PeopleResponse>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { peopleResponseList ->
                        val peopleList = peopleResponseList.map { it.person }

                        peopleRecyclerView.adapter = PersonAdapter(peopleList) { selectedPerson ->
                            val action =
                                PeopleListFragmentDirections.actionPeopleListFragmentToPersonDetailFragment(
                                    selectedPerson
                                )
                            findNavController().navigate(action)
                        }
                    } ?: run {
                        Toast.makeText(
                            requireContext(),
                            "No people found",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<List<PeopleResponse>>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Failed to fetch people: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

}