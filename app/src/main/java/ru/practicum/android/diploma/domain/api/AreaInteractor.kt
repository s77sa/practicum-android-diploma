package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.Area

interface AreaInteractor {
    suspend fun getCountries(): Pair<List<Area>?, String?>
    suspend fun getCities(countryId: String): Pair<List<Area>?, String?>
    suspend fun getCitiesAll(): Pair<List<Area>?, String?>
}
