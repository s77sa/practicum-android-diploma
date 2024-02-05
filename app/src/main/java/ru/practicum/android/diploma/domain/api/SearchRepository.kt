package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.util.Resource

interface SearchRepository {
    var vacancyCurrentPage: Int?
    var foundItems: Int?
    var pages: Int?
    fun searchVacancies(text: String, filter: Filter, page: Int): Flow<Resource<List<Vacancy>>>
    suspend fun getDetails(id: String): Resource<Vacancy>

}
