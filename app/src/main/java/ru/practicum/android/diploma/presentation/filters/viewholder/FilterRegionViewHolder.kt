package ru.practicum.android.diploma.presentation.filters.viewholder

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemCountryBinding
import ru.practicum.android.diploma.domain.models.Area

class FilterRegionViewHolder(private val binding: ItemCountryBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(region: Area) {
        binding.name.text = region.name
    }
}
