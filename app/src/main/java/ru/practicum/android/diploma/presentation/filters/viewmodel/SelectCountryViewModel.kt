package ru.practicum.android.diploma.presentation.filters.viewmodel

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.AreaInteractor
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.presentation.filters.states.CountrySelectionState
import ru.practicum.android.diploma.presentation.util.DataTransfer
import java.net.SocketException

class SelectCountryViewModel(
    private val areaInteractor: AreaInteractor,
    private val dataTransfer: DataTransfer,
) : ViewModel() {

    private val _countrySelectionState = MutableLiveData<CountrySelectionState>()
    val countrySelectionState: LiveData<CountrySelectionState> get() = _countrySelectionState

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getCountries() {
        viewModelScope.launch {
            _countrySelectionState.value = CountrySelectionState.Loading
            try {
                val (countries, _) = areaInteractor.getCountries()
                if (countries != null) {
                    if (countries.isEmpty()) {
                        _countrySelectionState.value = CountrySelectionState.NoData
                    } else {
                        _countrySelectionState.value = CountrySelectionState.Success(countries)
                    }
                } else {
                    _countrySelectionState.value = CountrySelectionState.ServerIssue
                }
            } catch (e: SocketException) {
                _countrySelectionState.value = CountrySelectionState.ServerIssue
            }
        }
    }

    fun applyCountryFilter(country: Country) {
        dataTransfer.setCountry(country)
    }
}
