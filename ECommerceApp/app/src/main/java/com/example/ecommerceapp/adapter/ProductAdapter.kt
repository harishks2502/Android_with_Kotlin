package com.example.ecommerceapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceapp.databinding.ItemProductBinding
import com.example.ecommerceapp.model.Product

class ProductAdapter(
    private val products: List<Product>,
    private val onAddToCartClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        val binding = holder.binding

        binding.productName.text = product.name
        binding.productImage.setImageResource(product.imageResourceId)
        binding.productPrice.text = "Price: ${product.price} Rs"
        binding.productCategory.text = "Category: ${product.category}"
        binding.quantityText.text = product.quantity.toString()

        binding.incrementButton.setOnClickListener {
            product.quantity++
            binding.quantityText.text = product.quantity.toString()
        }

        binding.decrementButton.setOnClickListener {
            if (product.quantity > 0) {
                product.quantity--
                binding.quantityText.text = product.quantity.toString()
            }
        }

        binding.addToCartButton.setOnClickListener {
            onAddToCartClick(product)
            product.quantity = 0
            binding.quantityText.text = "0"
        }
    }

    override fun getItemCount(): Int = products.size
}
