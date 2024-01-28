package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.ExternalNavigatorRepository
import ru.practicum.android.diploma.domain.api.SearchRepository
import ru.practicum.android.diploma.domain.api.VacancyInteractor

class VacancyInteractorImpl(
    private val repository: SearchRepository,
    private val externalNavigator: ExternalNavigatorRepository
):VacancyInteractor {

    override fun shareVacancy(url: String) {
        externalNavigator.intentShare(url)
    }
}
