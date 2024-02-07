package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.models.FilterSettings

interface FilterInteractor {
    fun load(): Filter?
    fun write(filter: Filter)
    fun loadFilterSettings(): FilterSettings?
    fun writeFilterSettings(filter: FilterSettings)
//    fun getCountries(): Flow<Resource<List<Country>>>
//    fun applyCountryFilter(country: Country)
//    fun getSelectedCountry(): Country
//    fun clearCountryFilter()
}
