package ru.practicum.android.diploma.presentation.filters.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.api.FilterInteractor
import ru.practicum.android.diploma.domain.api.RegionInteractor
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.presentation.filters.states.WorkplaceSelectionState
import ru.practicum.android.diploma.presentation.util.DataTransfer

class SelectWorkplaceViewModel(
    private val filtersInteractor: FilterInteractor,
    private val regionInteractor: RegionInteractor
) : ViewModel() {

    private val _countrySelectionState = MutableLiveData<WorkplaceSelectionState>()
    val countrySelectionState: LiveData<WorkplaceSelectionState> get() = _countrySelectionState

    private val _regionSelectionState = MutableLiveData<WorkplaceSelectionState>()
    val regionSelectionState: LiveData<WorkplaceSelectionState> get() = _regionSelectionState

    private val _countryData = MutableLiveData<Country>()
    val countryData get() = _countryData

    fun setCountryData(value: Country?) {
        DataTransfer.setCountry(value)
    }

    fun getCountryData() {
        _countryData.value = DataTransfer.getCountry()
    }

    fun getSelectedCountry() {
        val selectedCountry = filtersInteractor.getSelectedCountry()
        _countrySelectionState.value = WorkplaceSelectionState.CountryFilled(selectedCountry)
    }

    fun getSelectedRegion() {
        val selectedRegion = regionInteractor.getSelectedRegion()
        _regionSelectionState.value = WorkplaceSelectionState.RegionFilled(selectedRegion)
    }

    fun clearSelectedCountry() {
        filtersInteractor.clearCountryFilter()
        _countrySelectionState.value = WorkplaceSelectionState.Empty
    }

    fun clearSelectedRegion() {
        DataTransfer.setArea(null)
        _regionSelectionState.value = WorkplaceSelectionState.Empty
    }

    fun loadData() {
        val areaData = DataTransfer.getArea()
        if (areaData != null) {
            _regionSelectionState.value = WorkplaceSelectionState.RegionFilled(Region(areaData.id, areaData.name))
        }
    }
}
