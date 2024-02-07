package ru.practicum.android.diploma.presentation.filters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemCountryBinding
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.presentation.filters.viewholder.FilterRegionViewHolder

class FilterRegionAdapter(val onRegionClickedCB: (Area) -> Unit) : RecyclerView.Adapter<FilterRegionViewHolder>() {

    var regions = mutableListOf<Area>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterRegionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FilterRegionViewHolder(
            ItemCountryBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = regions.size

    override fun onBindViewHolder(holder: FilterRegionViewHolder, position: Int) {
        holder.bind(regions[position])
        holder.itemView.setOnClickListener {
            onRegionClickedCB(regions[position])
        }
    }
}
