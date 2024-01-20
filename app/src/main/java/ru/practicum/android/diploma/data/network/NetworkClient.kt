package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.VacancyResponse

interface NetworkClient {
    suspend fun doRequest(request: Map<String, String>): VacancyResponse
}