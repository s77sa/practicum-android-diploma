package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.util.Resource

interface VacancyInteractor {
    fun shareVacancy(url: String)
    suspend fun getDetailsById(id: String): Resource<Vacancy>

    suspend fun getDbDetailsById(id: String): Resource<Vacancy>
    suspend fun sendEmail(address: String, subject: String, text: String): String?
    suspend fun makeCall(number: String): String?
}
