package com.example.milestone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.navigation.fragment.findNavController
import com.example.milestone.databinding.FragmentDashboardBinding
import com.example.milestone.databinding.FragmentLoginBinding


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

        val transactions = listOf(
            "Transferred to Rahul: Rs.50",
            "Transferred to DMart: Rs. 500",
            "Credited by Manager: Rs.5000"
        )

        val adapter = android.widget.ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            transactions
        )

        binding.transactions.adapter = adapter

        binding.transactions.setOnItemClickListener { _, _, position, _ ->
            val selectedTransaction = transactions[position]
            val action =
                DashboardFragmentDirections.actionDashboardFragment2ToTransactionDetailFragment(
                    selectedTransaction
                )
            findNavController().navigate(action)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}