package ru.practicum.android.diploma.presentation.search.recyclerview

import android.content.Context
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.VacancyItemBinding
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyViewHolder(private val binding: VacancyItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private val salary: TextView = itemView.findViewById(R.id.salary_textView)

    fun bind(vacancy: Vacancy) {
        binding.vacancyTextView.text = vacancy.name + ", " + vacancy.city
        binding.employerTextView.text = vacancy.employer
        salary.text = formatSalary(vacancy.salaryFrom, vacancy.salaryTo, vacancy.salaryCurrency, salary.context)

        Glide.with(itemView).load(vacancy.employerLogoUrl)
            .placeholder(R.drawable.placeholder_vacancy) // DONE заменить на плэйсхолдер работодателя
            .fitCenter().into(binding.vacancyImageView)
    }

    private fun formatSalary(salaryFrom: Int?, salaryTo: Int?, salaryCurrency: String?, context: Context): String {
        var salary = ""
        if (salaryFrom != null) salary += "${context.getString(R.string.from)} $salaryFrom"
        if (salaryTo != null) salary += " ${context.getString(R.string.to)} $salaryTo"
        if (salaryCurrency != null) salary += " $salaryCurrency"
        if (salaryFrom == null && salaryTo == null) salary = context.getString(R.string.salary_not_specified)
        return salary
    }
}

