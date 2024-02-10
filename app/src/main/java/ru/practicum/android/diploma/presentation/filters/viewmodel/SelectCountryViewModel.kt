package ru.practicum.android.diploma.presentation.filters.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.AreaInteractor
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.presentation.filters.states.CountrySelectionState
import ru.practicum.android.diploma.presentation.util.DataTransfer

class SelectCountryViewModel(
    private val areaInteractor: AreaInteractor,
    private val dataTransfer: DataTransfer
) : ViewModel() {

    private val _countrySelectionState = MutableLiveData<CountrySelectionState>()
    val countrySelectionState: LiveData<CountrySelectionState> get() = _countrySelectionState

    fun getCountries() {
        viewModelScope.launch {
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
        }
    }

    fun applyCountryFilter(country: Country) {
        dataTransfer.setCountry(country)
    }
}
