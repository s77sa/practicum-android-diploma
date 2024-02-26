package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.AreaInteractor
import ru.practicum.android.diploma.domain.api.AreaRepository
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.presentation.util.Resource

class AreaInteractorImpl(private val repository: AreaRepository) : AreaInteractor {
    override suspend fun getCountries(): Pair<List<Area>?, String?> {
        val result = repository.getCountries()
        return if (result is Resource.Success) {
            Pair(result.data, null)
        } else {
            Pair(null, result.message)
        }
    }

    override suspend fun getCities(countryId: String): Pair<List<Area>?, String?> {
        val result = repository.getCities(countryId)
        return if (result is Resource.Success) {
            Pair(result.data, null)
        } else {
            Pair(null, result.message)
        }
    }

    override suspend fun getCitiesAll(): Pair<List<Area>?, String?> {
        val result = repository.getCitiesAll()
        return if (result is Resource.Success) {
            Pair(result.data, null)
        } else {
            Pair(null, result.message)
        }
    }
}
