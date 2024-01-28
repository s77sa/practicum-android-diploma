package ru.practicum.android.diploma.feature.favourite.domain.usecase

import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.api.FavouriteRepository

class GetVacancyByIdUseCase(private val favoriteRepository: FavouriteRepository) {
    suspend operator fun invoke(vacancyId: String): Vacancy? {
        return favoriteRepository.getVacancyById(vacancyId)
    }
}
