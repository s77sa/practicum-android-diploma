package ru.practicum.android.diploma.presentation.filters.states

import ru.practicum.android.diploma.domain.models.Country

sealed class CountrySelectionState {
    object Loading : CountrySelectionState()
    data class Success(val selectedCountry: List<Country>?) : CountrySelectionState()
    object Error : CountrySelectionState()
    object NoInternet : CountrySelectionState()
    object NoData : CountrySelectionState()
}
