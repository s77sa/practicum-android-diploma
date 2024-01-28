package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.api.SearchInteractor
import ru.practicum.android.diploma.domain.api.SearchRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.util.Resource

class SearchInteractorImpl(private val repository: SearchRepository) : SearchInteractor {
    override var vacancyCurrentPage: Int? = null
    override var foundItems: Int? = null
    override fun searchVacancies(request: Map<String, String>): Flow<Pair<List<Vacancy>?, String?>> {
        return repository.searchVacancies(request).map { result ->
            when (result) {
                is Resource.Success -> {
                    vacancyCurrentPage = repository.vacancyCurrentPage
                    foundItems = repository.foundItems
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }

    override suspend fun getDetails(id: String): Resource<Vacancy> {
        return repository.getDetails(id)
    }
}
