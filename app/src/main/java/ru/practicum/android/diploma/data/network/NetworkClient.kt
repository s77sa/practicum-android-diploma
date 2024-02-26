package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.AreaRequest
import ru.practicum.android.diploma.data.dto.Response

interface NetworkClient {
    suspend fun doRequest(request: Map<String, String>): Response
    suspend fun getVacancy(id: String): Response
    suspend fun getAreas(): Response
    suspend fun getNestedAreas(request: AreaRequest): Response
    suspend fun getIndustries(): Response
}
