package ru.practicum.android.diploma.feature.favourite.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.api.FavouriteRepository

class GetAllVacancyUseCase(private val favouriteRepository: FavouriteRepository) {
    operator fun invoke(): Flow<List<Vacancy>> {
        return favouriteRepository.getAllVacancy()
    }
}
