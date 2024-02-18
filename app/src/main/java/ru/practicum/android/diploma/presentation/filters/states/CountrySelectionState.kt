package ru.practicum.android.diploma.presentation.filters.states

import ru.practicum.android.diploma.domain.models.Area

sealed class CountrySelectionState {
    object Loading : CountrySelectionState()
    data class Success(val selectedCountry: List<Area>) : CountrySelectionState()
    object ServerIssue : CountrySelectionState()
    object NoData : CountrySelectionState()
}
