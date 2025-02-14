package com.example.cakerecipefinder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cakerecipefinder.R
import com.example.cakerecipefinder.model.Meal

class CakeAdapter(
    private var meals: List<Meal>,
    private val onClick: (Meal) -> Unit
) : RecyclerView.Adapter<CakeAdapter.CakeViewHolder>() {

    inner class CakeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(meal: Meal) {
            itemView.findViewById<TextView>(R.id.titleView).text = meal.strMeal
            itemView.findViewById<ImageView>(R.id.viewDetails).setOnClickListener { onClick(meal) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CakeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cake, parent, false)
        return CakeViewHolder(view)
    }

    override fun onBindViewHolder(holder: CakeViewHolder, position: Int) {
        val meal = meals[position]
        holder.bind(meal)
    }

    override fun getItemCount(): Int = meals.size
}
