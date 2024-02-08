package ru.practicum.android.diploma.presentation.filters.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.api.AreaInteractor
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.presentation.filters.states.CountrySelectionState
import ru.practicum.android.diploma.presentation.util.DataTransfer

class SelectCountryViewModel(
    private val areaInteractor: AreaInteractor,
) : ViewModel() {

    private val _countrySelectionState = MutableLiveData<CountrySelectionState>()
    val countrySelectionState: LiveData<CountrySelectionState> get() = _countrySelectionState

    private var selectedCountry: String = ""

    suspend fun getCountries(): List<Area> {
        _countrySelectionState.value = CountrySelectionState.Loading
        val (countries, error) = areaInteractor.getCountries()
        if (countries != null) {
            if (countries.isEmpty()) {
                _countrySelectionState.value = CountrySelectionState.NoData
            } else {
                _countrySelectionState.value = CountrySelectionState.Success(countries)
            }
        } else {
            _countrySelectionState.value = CountrySelectionState.ServerIssue
        }
        return countries ?: emptyList()
    }

    fun applyCountryFilter(country: Country) {
        selectedCountry = country.name
        DataTransfer.setCountry(country)
        _countrySelectionState.value = CountrySelectionState.Success(listOf())

    }
}
