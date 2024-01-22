package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.Resource

interface SearchRepository {
    fun searchVacancies(request: Map<String, String>): Flow<Resource<List<Vacancy>>>
}
