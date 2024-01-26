package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.util.Resource

interface SearchInteractor {
    fun searchVacancies(request: Map<String, String>): Flow<Pair<List<Vacancy>?, String?>>
    suspend fun getDetails(id: String): Resource<Vacancy>
}
