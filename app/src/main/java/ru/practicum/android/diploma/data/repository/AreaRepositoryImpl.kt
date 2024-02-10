package ru.practicum.android.diploma.data.repository

import ru.practicum.android.diploma.data.conventers.AreaMapper
import ru.practicum.android.diploma.data.dto.AreaRequest
import ru.practicum.android.diploma.data.dto.AreaResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.api.AreaRepository
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.presentation.util.Resource

class AreaRepositoryImpl(
    private val networkClient: NetworkClient,
    private val converter: AreaMapper
) : AreaRepository {
    override suspend fun getCountries(): Resource<List<Area>> {
        val response = networkClient.getAreas()
        return if (response.resultCode == NO_CONNECTION) {
            Resource.Error("No internet connection")
        } else if (response.resultCode == SUCCESS) {
            val areas = response as AreaResponse
            Resource.Success(converter.map(areas))
        } else {
            Resource.Error("Network error")
        }
    }

    override suspend fun getCities(expression: String): Resource<List<Area>> {
        val response = networkClient.getNestedAreas(AreaRequest(expression))
        return if (response.resultCode == NO_CONNECTION) {
            Resource.Error("No internet connection")
        } else if (response.resultCode == SUCCESS) {
            val areas = response as AreaResponse
            Resource.Success(converter.mapCity(areas))
        } else {
            Resource.Error("Network error")
        }
    }

    override suspend fun getCitiesAll(): Resource<List<Area>> {
        val response = networkClient.getAreas()
        return if (response.resultCode == NO_CONNECTION) {
            Resource.Error("No internet connection")
        } else if (response.resultCode == SUCCESS) {
            val areas = response as AreaResponse
            Resource.Success(converter.mapCityAll(areas))
        } else {
            Resource.Error("Network error")
        }
    }
}
