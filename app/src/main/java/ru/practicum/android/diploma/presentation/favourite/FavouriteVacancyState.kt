package ru.practicum.android.diploma.presentation.favourite

import ru.practicum.android.diploma.domain.models.Vacancy

sealed class FavouriteVacancyState {
    data object Empty : FavouriteVacancyState()
    data class VacancyLoaded(val vacancy: List<Vacancy>) : FavouriteVacancyState()
}
