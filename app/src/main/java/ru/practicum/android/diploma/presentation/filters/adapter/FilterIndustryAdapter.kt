package ru.practicum.android.diploma.presentation.filters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemIndustryBinding
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.presentation.filters.viewholder.FilterIndustryViewHolder

class FilterIndustryAdapter(val onIndustryClickedCB: (Industry) -> Unit) :
    RecyclerView.Adapter<FilterIndustryViewHolder>() {
    var industries = mutableListOf<Industry>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterIndustryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemIndustryBinding.inflate(layoutInflater, parent, false)
        return FilterIndustryViewHolder(binding)
    }

    override fun getItemCount() = industries.size

    override fun onBindViewHolder(holder: FilterIndustryViewHolder, position: Int) {
        val industry = industries[position]
        holder.bind(industry)

        holder.itemView.setOnClickListener {
            onIndustryClickedCB(industry)
        }
    }
}