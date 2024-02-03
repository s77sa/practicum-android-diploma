package ru.practicum.android.diploma.filter.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemCountryBinding
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.filter.presentation.viewholder.FilterRegionViewHolder

class FilterRegionAdapter(val onRegionClickedCB: (Region) -> Unit) : RecyclerView.Adapter<FilterRegionViewHolder>() {

    var regions = mutableListOf<Region>()

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
