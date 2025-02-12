package com.example.ecommerceapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.FragmentCartBinding

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val args: CartFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productList = StringBuilder()

        val cart = args.cart

        cart.products.forEach { productDetail ->
            if (productDetail.quantity > 0) {
                productList.append("${productDetail.name}\n${" ".repeat(12)}Price = ${productDetail.price}, Quantity = ${productDetail.quantity}\n")
            }
        }

        if (productList.isEmpty()) {
            binding.productsList.text = "No products to display"
        } else {
            binding.productsList.text = productList.toString()
        }

        val totalCost = cart.products.sumOf { it.price * it.quantity }
        binding.productsTotalCost.text = "Total Cost: $totalCost"

        binding.cancelButton.setOnClickListener {
            Toast.makeText(requireContext(), "Order Cancelled Successfully", Toast.LENGTH_SHORT)
                .show()
            findNavController().navigate(R.id.action_cartFragment_to_productsFragment)
        }

        binding.purchaseButton.setOnClickListener {
            Toast.makeText(requireContext(), "Order Purchased Successfully", Toast.LENGTH_SHORT)
                .show()
            findNavController().navigate(R.id.action_cartFragment_to_productsFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
