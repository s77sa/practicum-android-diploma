package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.presentation.util.Resource

interface AreaRepository {
    suspend fun getCountries(): Resource<List<Area>>
    suspend fun getCities(countryId: String): Resource<List<Area>>
    suspend fun getCitiesAll(): Resource<List<Area>>
}
