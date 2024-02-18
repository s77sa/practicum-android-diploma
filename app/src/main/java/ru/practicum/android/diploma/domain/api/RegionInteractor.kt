package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.presentation.util.Resource

interface RegionInteractor {
    fun getRegions(countryId: String): Flow<Resource<List<Region>>>
    fun applyRegionFilter(region: Region)
    fun searchRegionByName(regionName: String): Flow<Resource<List<Region>>>
    fun getSelectedRegion(): Region
    fun clearRegionFilter()
}
