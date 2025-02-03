package com.example.persondetailsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.persondetailsapp.R
import com.example.persondetailsapp.model.Person

class PersonAdapter(
    private val people: List<Person>,
    private val onClick: (Person) -> Unit
) : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    inner class PersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val personNameView = itemView.findViewById<TextView>(R.id.personNameView)
        private val viewDetailsButton = itemView.findViewById<Button>(R.id.viewDetailsButton)

        fun bind(person: Person) {
            personNameView.text = person.name
            viewDetailsButton.setOnClickListener { onClick(person) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_person, parent, false)
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = people[position]
        holder.bind(person)
    }

    override fun getItemCount(): Int = people.size
}