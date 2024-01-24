package ru.practicum.android.diploma.presentation.search

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.LayoutSearchVacancyBinding
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyViewHolder(private val binding: LayoutSearchVacancyBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(vacancy: Vacancy) {
        binding.name.text = vacancy.name
        binding.city.text = vacancy.city
        binding.employer.text = vacancy.employer
        binding.salaryFrom.text = vacancy.salaryFrom.toString()
        binding.salaryTo.text = vacancy.salaryTo.toString()
        binding.salaryCurrency.text = vacancy.salaryCurrency

        Glide.with(itemView).load(vacancy.employerLogoUrl)
            .placeholder(R.drawable.ic_main_menu) // ToDo заменить на плэйсхолдер работодателя
            .centerCrop().into(binding.logo)
    }
}
