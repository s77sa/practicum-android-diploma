package ru.practicum.android.diploma.presentation.filters.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.presentation.filters.states.WorkplaceSelectionState
import ru.practicum.android.diploma.presentation.util.DataTransfer

class SelectWorkplaceViewModel(
    private val dataTransfer: DataTransfer
) : ViewModel() {
    private val _countrySelectionState = MutableLiveData<WorkplaceSelectionState>()
    val countrySelectionState: LiveData<WorkplaceSelectionState> get() = _countrySelectionState

    private val _regionSelectionState = MutableLiveData<WorkplaceSelectionState>()
    val regionSelectionState: LiveData<WorkplaceSelectionState> get() = _regionSelectionState

    private val _countryData = MutableLiveData<Country>()
    val countryData: LiveData<Country> get() = _countryData

    fun getCountryData() {
        _countryData.value = dataTransfer.getCountry()
    }

    fun saveCountry(country: Country?) {
        dataTransfer.setCountry(country)
        Log.d("countryData", " Save ${DataTransfer.getCountry()}")
    }

    fun saveRegion(region: Area?) {
        dataTransfer.setArea(region)
    }

    fun clearSelectedCountry() {
        dataTransfer.setCountry(null)
        _countrySelectionState.value = WorkplaceSelectionState.Empty
    }

    fun clearSelectedRegion() {
        dataTransfer.setArea(null)
        _regionSelectionState.value = WorkplaceSelectionState.Empty
    }

    fun loadData() {
        val areaData = dataTransfer.getArea()
        val countryData = dataTransfer.getCountry()

        if (areaData != null) {
            if (areaData.country != null) {
                _regionSelectionState.value = WorkplaceSelectionState.RegionFilled(
                    Region(
                        areaData.id,
                        areaData.name,
                        areaData.country.id,
                        areaData.country.name
                    )
                )
            } else {
                _regionSelectionState.value = WorkplaceSelectionState.RegionFilled(
                    Region(
                        areaData.id,
                        areaData.name,
                    )
                )
            }
        }

        if (countryData != null) {
            _countrySelectionState.value =
                WorkplaceSelectionState.CountryFilled(Country(countryData.id, countryData.name))
        }
    }
}
