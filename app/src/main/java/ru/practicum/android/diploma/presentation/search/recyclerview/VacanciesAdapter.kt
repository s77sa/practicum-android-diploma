package ru.practicum.android.diploma.presentation.search.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ProgressbarItemBinding
import ru.practicum.android.diploma.databinding.VacancyItemBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.util.IsLastPage

class VacanciesAdapter(
    private val clickListener: (vacancy: Vacancy) -> Unit,
    var vacancies: ArrayList<Vacancy>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_DATA) {
            VacancyViewHolder(
                VacancyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        } else {
            ProgressbarViewHolder(
                ProgressbarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    override fun getItemCount() = vacancies.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is VacancyViewHolder) {
            holder.bind(vacancies[position])
            holder.itemView.setOnClickListener { this@VacanciesAdapter.clickListener(vacancies[position]) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < itemCount - 1) {
            ITEM_DATA
        } else {
            if (IsLastPage.IS_LAST_PAGE) ITEM_PROGRESSBAR else ITEM_DATA
        }
    }

    companion object {
        private const val ITEM_DATA = 0
        private const val ITEM_PROGRESSBAR = 1
    }
}
