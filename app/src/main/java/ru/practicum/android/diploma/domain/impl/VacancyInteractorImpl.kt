package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.ExternalNavigatorRepository
import ru.practicum.android.diploma.domain.api.SearchRepository
import ru.practicum.android.diploma.domain.api.VacancyInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.util.Resource

class VacancyInteractorImpl(
    private val repository: SearchRepository,
    private val externalNavigator: ExternalNavigatorRepository,
) : VacancyInteractor {
    override suspend fun getDetailsById(id: String): Resource<Vacancy> {
        return repository.getDetails(id)
    }

    override fun shareVacancy(url: String) {
        externalNavigator.intentShare(url)
    }
}