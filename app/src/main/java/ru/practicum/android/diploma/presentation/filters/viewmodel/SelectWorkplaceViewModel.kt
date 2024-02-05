package ru.practicum.android.diploma.presentation.filters.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.api.FilterInteractor
import ru.practicum.android.diploma.presentation.filters.states.WorkplaceSelectionState

class SelectWorkplaceViewModel(private val filtersInteractor: FilterInteractor) : ViewModel() {

    private val _countrySelectionState = MutableLiveData<WorkplaceSelectionState>()
    val countrySelectionState: LiveData<WorkplaceSelectionState> get() = _countrySelectionState

    private val _regionSelectionState = MutableLiveData<WorkplaceSelectionState>()
    val regionSelectionState: LiveData<WorkplaceSelectionState> get() = _regionSelectionState

    fun getSelectedCountry() {
        val selectedCountry = filtersInteractor.getSelectedCountry()
        _countrySelectionState.value = WorkplaceSelectionState.CountryFilled(selectedCountry)
    }

    fun getSelectedRegion() {
        val selectedRegion = filtersInteractor.getSelectedRegion()
        _regionSelectionState.value = WorkplaceSelectionState.RegionFilled(selectedRegion)
    }

    fun clearSelectedCountry() {
        filtersInteractor.clearCountryFilter()
        _countrySelectionState.value = WorkplaceSelectionState.Empty
    }

    fun clearSelectedRegion() {
        filtersInteractor.clearRegionFilter()
        _regionSelectionState.value = WorkplaceSelectionState.Empty
    }
}
