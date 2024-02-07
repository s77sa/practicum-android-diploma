package ru.practicum.android.diploma.presentation.filters.viewmodel

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.api.AreaInteractor
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.presentation.filters.states.RegionSelectionState
import ru.practicum.android.diploma.presentation.util.DataTransfer

class SelectRegionViewModel(
    private val areaInteractor: AreaInteractor,
    private val context: Context,
    private val dataTransfer: DataTransfer
) : ViewModel() {

    private val regionSelectionState = MutableLiveData<RegionSelectionState>()
    fun regionSelectionState(): LiveData<RegionSelectionState> = regionSelectionState

    private var selectedRegion: String = ""

    fun getRegions(countryId: String?) {
        regionSelectionState.value = RegionSelectionState.Loading
        if (countryId != null) {
            viewModelScope.launch {
                val regions = areaInteractor.getCities(countryId)
                Log.i("Region", "getRegions $regions")
                processRegionResult(regions.first, regions.second)
            }
        } else {
            getAllRegions()
        }

    }

    private fun getAllRegions() {
        viewModelScope.launch {
            val regions = areaInteractor.getCitiesAll()
            Log.i("Region", "getRegions $regions")
            processRegionResult(regions.first, regions.second)
        }
    }

    fun applyRegionFilter(region: Area) {

    }

    fun selectRegion(region: Area) {
        dataTransfer.setArea(region)
    }

    fun getSelectedRegion(): String {
        return selectedRegion
    }

    fun searchRegionByName(regionName: String) {

    }

    fun processRegionResult(found: List<Area>?, errorMessage: String?) {

        val areaList = mutableListOf<Area>()
        if (found != null) {
            areaList.clear()
            areaList.addAll(found)
        }
        when {
            errorMessage != null -> {
                if (errorMessage == getString(context, R.string.no_internet)) {
                    regionSelectionState.postValue(RegionSelectionState.NoInternet)

                } else {
                    regionSelectionState.postValue(RegionSelectionState.Error)

                }

            }

            areaList.isEmpty() -> {
                regionSelectionState.postValue(RegionSelectionState.NoData)
            }

            else -> {
                regionSelectionState.postValue(RegionSelectionState.Success(areaList))
            }
        }

    }
}
