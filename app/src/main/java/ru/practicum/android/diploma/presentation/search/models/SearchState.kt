package ru.practicum.android.diploma.presentation.search.models

import ru.practicum.android.diploma.domain.models.Vacancy

sealed interface SearchState {
    data object Loading : SearchState

    data class Content(
        val vacancies: List<Vacancy>,
        val foundItems: Int?
    ) : SearchState

    data class Error(
        val errorMessage: String
    ) : SearchState

    data class Empty(
        val message: String
    ) : SearchState

}
