package ru.practicum.android.diploma.filter.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemIndustryBinding
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.filter.presentation.viewholder.FilterIndustryViewHolder

class FilterIndustryAdapter (val onIndustryClickedCB: (Industry) -> Unit) : RecyclerView.Adapter<FilterIndustryViewHolder>() {

    var industries = mutableListOf<Industry>()
    var selectedIndustry: Industry? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterIndustryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FilterIndustryViewHolder(
            ItemIndustryBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = industries.size

    override fun onBindViewHolder(holder: FilterIndustryViewHolder, position: Int) {

        val clickListener = View.OnClickListener() {

            industries[position].isChecked = !industries[position].isChecked
            selectedIndustry = industries[position]
            industries.forEach {
                it.isChecked = (it == industries[position])
            }

            notifyDataSetChanged()

            onIndustryClickedCB(industries[position])
        }

        if (selectedIndustry != null) {
            if (industries[position].id == selectedIndustry!!.id) {
                industries[position].isChecked = true
            }
        }
        holder.bind(industries[position])
        holder.rbIndustry.setOnClickListener(clickListener)
        holder.itemView.setOnClickListener(clickListener)
    }
}
