package ru.practicum.android.diploma.presentation.filters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemCountryBinding
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.presentation.filters.viewholder.FilterCountryViewHolder

class FilterCountryAdapter(private val onCountryClick: (Country) -> Unit) :
    RecyclerView.Adapter<FilterCountryViewHolder>() {

    var countries = mutableListOf<Country>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterCountryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCountryBinding.inflate(layoutInflater, parent, false)
        return FilterCountryViewHolder(binding, onCountryClick)
    }

    override fun getItemCount() = countries.size

    override fun onBindViewHolder(holder: FilterCountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }
}
