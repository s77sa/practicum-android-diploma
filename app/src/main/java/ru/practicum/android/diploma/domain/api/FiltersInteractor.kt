package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.presentation.util.Resource

interface FiltersInteractor {
    fun getCountries(): Flow<Resource<List<Country>>>
    fun getRegions(countryId: String): Flow<Resource<List<Region>>>
    fun applyCountryFilter(country: Country)
    fun applyRegionFilter(region:Region)
    fun searchRegionByName(regionName: String): Flow<Resource<List<Region>>>
    fun getSelectedRegion(): Region
    fun getSelectedCountry(): Country
    fun clearCountryFilter()
    fun clearRegionFilter()
}
