package ru.practicum.android.diploma.presentation.filters.viewholder

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemCountryBinding
import ru.practicum.android.diploma.domain.models.Country

class FilterCountryViewHolder(
    private val binding: ItemCountryBinding,
    private val onItemClick: (Country) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(country: Country) {
        binding.name.text = country.name
        binding.root.setOnClickListener {
            onItemClick(country)
        }
    }
}
