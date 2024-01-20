package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.dto.VacancyResponse

interface NetworkClient {

    suspend fun doRequest(dto:Map<String, String>): VacancyResponse

}
