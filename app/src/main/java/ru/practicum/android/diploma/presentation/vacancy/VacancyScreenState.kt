package ru.practicum.android.diploma.presentation.vacancy

import ru.practicum.android.diploma.domain.models.Vacancy

sealed class VacancyScreenState {
    data class Success(val vacancy: Vacancy) : VacancyScreenState()
    data class Error(val errorMessage: String) : VacancyScreenState()
    object Loading : VacancyScreenState()
}
