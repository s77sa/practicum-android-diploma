package ru.practicum.android.diploma.presentation.filters.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.presentation.util.Resource

class SelectIndustryViewModel : ViewModel() {

    fun getCountries(): Flow<Resource<List<Country>>> {
        return TODO("Provide the return value")
    }

    fun getRegions(countryId: String): Flow<Resource<List<Region>>> {
        return TODO("Provide the return value")
    }

    fun applyCountryFilter(country: Country) {}
    fun applyRegionFilter(region: Region) {}

}
