package ru.practicum.android.diploma.presentation.filters.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.RegionInteractor
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.presentation.filters.states.RegionSelectionState
import ru.practicum.android.diploma.presentation.util.Resource

class SelectRegionViewModel(
    private val regionInteractor: RegionInteractor,
) : ViewModel() {

    private val regionSelectionState = MutableLiveData<RegionSelectionState>()
    fun regionSelectionState(): LiveData<RegionSelectionState> = regionSelectionState

    private var selectedRegion: String = ""

    fun getRegions(countryId: String) {
        regionSelectionState.value = RegionSelectionState.Loading
        viewModelScope.launch {
            regionInteractor.getRegions(countryId).collect { resource ->
                processRegionResult(resource)
            }
        }
    }

    fun applyRegionFilter(region: Region) {
        viewModelScope.launch {
            regionInteractor.applyRegionFilter(region)
            getRegions(region.countryId)
        }
    }

    fun getSelectedRegion(): String {
        return selectedRegion
    }

    fun searchRegionByName(regionName: String) {
        viewModelScope.launch {
            regionInteractor.searchRegionByName(regionName).collect { resource ->
                processRegionResult(resource)
            }
        }
    }

    fun processRegionResult(resource: Resource<List<Region>>) {
        when (resource) {
            is Resource.Success -> {
                val regions = resource.data ?: emptyList()
                if (regions.isNotEmpty()) {
                    regionSelectionState.value = RegionSelectionState.Success(regions)
                } else {
                    regionSelectionState.value = RegionSelectionState.NoData
                }
            }

            is Resource.Error -> {
                regionSelectionState.value = RegionSelectionState.Error
            }
        }
    }
}
