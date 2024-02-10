package ru.practicum.android.diploma.presentation.filters.viewmodel

import android.content.Context
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.api.AreaInteractor
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.presentation.filters.SelectRegionFragment.Companion.SEARCH_DEBOUNCE_DELAY_MILS
import ru.practicum.android.diploma.presentation.filters.states.RegionSelectionState
import ru.practicum.android.diploma.presentation.util.DataTransfer
import ru.practicum.android.diploma.presentation.util.debounce

class SelectRegionViewModel(
    id: String,
    private val areaInteractor: AreaInteractor,
    private val context: Context,
    private val dataTransfer: DataTransfer
) : ViewModel() {

    private var countryId = id

    private val regionSelectionState = MutableLiveData<RegionSelectionState>()
    fun regionSelectionState(): LiveData<RegionSelectionState> = regionSelectionState

    private var selectedRegion: String = ""

    private var foundRegions: MutableList<Area>? = null

    init {
        getData()
    }

    fun getData() {
        getRegions(countryId)
    }

    fun searchDebounce(changedText: String) {
        regionSearchDebounce(changedText)
    }

    private val regionSearchDebounce = debounce<String>(
        SEARCH_DEBOUNCE_DELAY_MILS,
        viewModelScope,
        true
    ) { changedText ->
        searchRegionByName(changedText)
    }

    fun getRegions(countryId: String?) {
        regionSelectionState.value = RegionSelectionState.Loading
        if (countryId != "") {
            viewModelScope.launch {
                val regions = countryId?.let { areaInteractor.getCities(it) }
                if (regions != null) {
                    processRegionResult(regions.first, regions.second)
                }
            }
        } else {
            getAllRegions()
        }
    }

    private fun getAllRegions() {
        viewModelScope.launch {
            val regions = areaInteractor.getCitiesAll()
            processRegionResult(regions.first, regions.second)
        }
    }

    fun searchRegionByName(text: String) {
        regionSelectionState.value = RegionSelectionState.Loading
        viewModelScope.launch {
            val filteredRegions = foundRegions?.filter { it.name.contains(text, ignoreCase = true) }
            if (filteredRegions!!.isEmpty()) {
                regionSelectionState.postValue(RegionSelectionState.NoData)
            } else {
                regionSelectionState.postValue(RegionSelectionState.Success(filteredRegions))
            }

        }
    }

    fun selectRegion(region: Area) {
        dataTransfer.setArea(region)
    }

    fun getSelectedRegion(): String {
        return selectedRegion
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
                foundRegions = areaList
                regionSelectionState.postValue(RegionSelectionState.Success(areaList))
            }
        }

    }

}
