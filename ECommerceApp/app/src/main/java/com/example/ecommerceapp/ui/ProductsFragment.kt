package com.example.ecommerceapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceapp.R
import com.example.ecommerceapp.adapter.ProductAdapter
import com.example.ecommerceapp.databinding.FragmentProductsBinding
import com.example.ecommerceapp.model.Cart
import com.example.ecommerceapp.model.Product
import com.example.ecommerceapp.model.ProductDetail
import java.util.UUID

class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    private val products = mutableListOf(
        Product(UUID.randomUUID().toString(), "Laptop", 65000.0, 0, R.drawable.laptop, "Electronics"),
        Product(UUID.randomUUID().toString(), "Harry Potter Books", 12500.0, 0, R.drawable.harry_potter_books, "Books"),
        Product(UUID.randomUUID().toString(), "Television", 80000.0, 0, R.drawable.television, "Electronics")
    )

    private val cartItems = mutableListOf<ProductDetail>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val priceFilterOptions = arrayOf("Price: Low to High", "Price: High to Low")
        val priceAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, priceFilterOptions)
        priceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.priceFilterSpinner.adapter = priceAdapter

        binding.priceFilterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>, selectedView: View?,
                position: Int, id: Long
            ) {
                when (priceFilterOptions[position]) {
                    "Price: Low to High" -> sortProductsByPriceAscending()
                    "Price: High to Low" -> sortProductsByPriceDescending()
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {}
        }

        val categoryFilterOptions = arrayOf("All", "Electronics", "Books")
        val categoryAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoryFilterOptions)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categoryFilterSpinner.adapter = categoryAdapter

        binding.categoryFilterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>, selectedView: View?,
                position: Int, id: Long
            ) {
                when (categoryFilterOptions[position]) {
                    "All" -> filterProductsByCategory("All")
                    "Electronics" -> filterProductsByCategory("Electronics")
                    "Books" -> filterProductsByCategory("Books")
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {}
        }

        binding.productsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val productAdapter = ProductAdapter(products) { product ->
            val productDetail = ProductDetail(
                quantity = product.quantity,
                price = product.price,
                name = product.name
            )
            updateCart(productDetail)
        }
        binding.productsRecyclerView.adapter = productAdapter

        binding.cartSectionButton.setOnClickListener {
            val cart = Cart(cartItems)
            val action = ProductsFragmentDirections.actionProductsFragmentToCartFragment(cart)
            findNavController().navigate(action)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun sortProductsByPriceAscending() {
        products.sortBy { it.price }
        binding.productsRecyclerView.adapter?.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun sortProductsByPriceDescending() {
        products.sortByDescending { it.price }
        binding.productsRecyclerView.adapter?.notifyDataSetChanged()
    }

    private fun filterProductsByCategory(category: String) {
        val filteredProducts = when (category) {
            "Electronics" -> products.filter { it.category == "Electronics" }
            "Books" -> products.filter { it.category == "Books" }
            else -> products
        }
        updateProductList(filteredProducts)
    }

    private fun updateProductList(filteredProducts: List<Product>) {
        val productAdapter = ProductAdapter(filteredProducts) { product ->
            val productDetail = ProductDetail(
                quantity = product.quantity,
                price = product.price,
                name = product.name
            )
            updateCart(productDetail)
        }
        binding.productsRecyclerView.adapter = productAdapter
    }

    private fun updateCart(productDetail: ProductDetail) {
        val existingItem = cartItems.find { it.name == productDetail.name }
        if (existingItem != null) {
            existingItem.quantity = productDetail.quantity
        } else {
            cartItems.add(productDetail)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
