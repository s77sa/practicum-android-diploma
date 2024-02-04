package ru.practicum.android.diploma.presentation.filters.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.api.FiltersInteractor
import ru.practicum.android.diploma.presentation.filters.states.WorkplaceSelectionState

class SelectWorkplaceViewModel(private val filtersInteractor: FiltersInteractor) : ViewModel() {

    private val _workplaceSelectionState = MutableLiveData<WorkplaceSelectionState>()
    val workplaceSelectionState: LiveData<WorkplaceSelectionState> get() = _workplaceSelectionState

    private fun setWorkplaceSelectionState(state: WorkplaceSelectionState) {
        _workplaceSelectionState.value = state
    }

    fun getSelectedCountry() {
        val selectedCountry = filtersInteractor.getSelectedCountry()
        setWorkplaceSelectionState(
            WorkplaceSelectionState.Filled(
                selectedCountry,
                filtersInteractor.getSelectedRegion()
            )
        )
    }

    fun getSelectedRegion() {
        val selectedRegion = filtersInteractor.getSelectedRegion()
        setWorkplaceSelectionState(
            WorkplaceSelectionState.Filled(
                filtersInteractor.getSelectedCountry(),
                selectedRegion
            )
        )
    }

    fun clearSelectedCountry() {
        filtersInteractor.clearCountryFilter()
        setWorkplaceSelectionState(WorkplaceSelectionState.Empty)
    }

    fun clearSelectedRegion() {
        filtersInteractor.clearRegionFilter()
        setWorkplaceSelectionState(WorkplaceSelectionState.Empty)
    }
}
