package ru.practicum.android.diploma.data.repository

import ru.practicum.android.diploma.data.conventers.IndustryMapper
import ru.practicum.android.diploma.data.dto.IndustryResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.api.IndustryRepository
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.presentation.util.Resource

class IndustryRepositoryImpl(
    private val networkClient: NetworkClient,
    private val mapper: IndustryMapper
) : IndustryRepository {
    override suspend fun getIndustries(): Resource<List<Industry>> {
        val response = networkClient.getIndustries()
        return when (response.resultCode) {
            NO_CONNECTION -> {
                Resource.Error("No internet connection")
            }

            SUCCESS -> {
                with(response as IndustryResponse) {
                    Resource.Success(response.items.let { mapper.map(it) })
                }
            }

            else -> {
                Resource.Error("Error ${response.resultCode}")
            }
        }
    }
}
