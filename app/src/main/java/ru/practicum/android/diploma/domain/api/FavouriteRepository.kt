package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

interface FavouriteRepository {
    suspend fun insertVacancy(vacancyFull: Vacancy)

    suspend fun deleteVacancy(vacancyFull: Vacancy)

    fun getAllVacancy(): Flow<List<Vacancy>>

    fun getAllVacancyIds(): Flow<List<String>>

    suspend fun getVacancyById(id: String): Vacancy?
}
