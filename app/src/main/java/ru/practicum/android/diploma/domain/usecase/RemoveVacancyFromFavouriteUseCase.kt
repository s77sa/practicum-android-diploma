package ru.practicum.android.diploma.feature.favourite.domain.usecase

import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.api.FavouriteRepository

class RemoveVacancyFromFavouriteUseCase(private val favoriteRepository: FavouriteRepository) {
    suspend operator fun invoke(vacancyFull: Vacancy) {
        favoriteRepository.deleteVacancy(vacancyFull)
    }
}
