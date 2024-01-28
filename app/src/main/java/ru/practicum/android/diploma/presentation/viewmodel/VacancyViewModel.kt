package ru.practicum.android.diploma.presentation.viewmodel

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.api.VacancyInteractor

class VacancyViewModel(private val interactor: VacancyInteractor) : ViewModel() {


    fun shareVacancy(id: String?) {
        interactor.shareVacancy(HH_URL + id)
    }

    companion object {
        const val HH_URL = "https://hh.ru/vacancy/"
    }
}
