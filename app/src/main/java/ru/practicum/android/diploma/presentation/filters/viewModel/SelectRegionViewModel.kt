package ru.practicum.android.diploma.presentation.filters.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.FiltersInteractor
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.presentation.filters.states.RegionSelectionState
import ru.practicum.android.diploma.presentation.util.Resource

class SelectRegionViewModel(
    private val filtersInteractor: FiltersInteractor,
) : ViewModel() {

    private val _regionSelectionState = MutableLiveData<RegionSelectionState?>()
    val regionSelectionState: LiveData<RegionSelectionState?> get() = _regionSelectionState

    private var selectedRegion: String = ""

    private fun getRegions(countryId: String) {
        _regionSelectionState.value = RegionSelectionState.Loading
        viewModelScope.launch {
            filtersInteractor.getRegions(countryId).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val regions = resource.data ?: emptyList()
                        if (regions.isNotEmpty()) {
                            _regionSelectionState.value = RegionSelectionState.Success(regions)
                        } else {
                            _regionSelectionState.value = RegionSelectionState.NoData
                        }
                    }

                    is Resource.Error -> {
                        _regionSelectionState.value = RegionSelectionState.Error
                    }
                }
            }
        }
    }

    fun applyRegionFilter(region: Region) {
        viewModelScope.launch {
            filtersInteractor.applyRegionFilter(region)
            getRegions(region.countryId)
        }
    }

    fun getSelectedRegion(): String {
        return selectedRegion
    }

    private fun searchRegionByName(regionName: String) {
        viewModelScope.launch {
            getRegions(regionName)
        }
    }
}
