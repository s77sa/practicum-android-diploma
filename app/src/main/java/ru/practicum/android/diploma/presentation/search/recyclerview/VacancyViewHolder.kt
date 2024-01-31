package ru.practicum.android.diploma.presentation.search.recyclerview

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.VacancyItemBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.util.SalaryUtils

class VacancyViewHolder(private val binding: VacancyItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private val salary: TextView = itemView.findViewById(R.id.salary_textView)

    fun bind(vacancy: Vacancy) {
        binding.vacancyTextView.text = vacancy.name + ", " + vacancy.city
        binding.employerTextView.text = vacancy.employer
        salary.text = SalaryUtils.formatSalary(
            salary.context,
            vacancy.salaryFrom,
            vacancy.salaryTo,
            vacancy.salaryCurrency
        )

        Glide.with(itemView).load(vacancy.employerLogoUrl)
            .placeholder(R.drawable.placeholder_vacancy) // DONE заменить на плэйсхолдер работодателя
            .fitCenter().into(binding.vacancyImageView)
    }
}
