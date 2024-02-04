package ru.practicum.android.diploma.presentation.filters.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.FiltersInteractor
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.presentation.filters.states.CountrySelectionState
import ru.practicum.android.diploma.presentation.util.Resource

class SelectCountryViewModel(
    private val filtersInteractor: FiltersInteractor
) : ViewModel() {

    private val _countrySelectionState = MutableLiveData<CountrySelectionState>()
    val countrySelectionState: LiveData<CountrySelectionState> get() = _countrySelectionState

    private var selectedCountry: String = ""

    private fun getCountries() {
        _countrySelectionState.value = CountrySelectionState.Loading
        viewModelScope.launch {
            filtersInteractor.getCountries().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val countries = resource.data ?: emptyList()
                        if (countries.isNotEmpty()) {
                            _countrySelectionState.value = CountrySelectionState.Success(countries)
                        } else {
                            _countrySelectionState.value = CountrySelectionState.NoData
                        }
                    }
                    is Resource.Error -> {
                        _countrySelectionState.value = CountrySelectionState.Error
                    }
                }
            }
        }
    }

    fun applyCountryFilter(country: Country) {
        viewModelScope.launch {
            filtersInteractor.applyCountryFilter(country)
            getCountries()
        }
    }

    fun getSelectedCountry(): String {
        return selectedCountry
    }
}
