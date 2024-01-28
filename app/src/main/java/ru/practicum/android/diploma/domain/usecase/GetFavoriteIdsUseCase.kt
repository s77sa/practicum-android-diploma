package ru.practicum.android.diploma.feature.favourite.domain.usecase


import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.FavouriteRepository

class GetFavoriteIdsUseCase(private val favoriteRepository: FavouriteRepository) {
    operator fun invoke(): Flow<List<String>> {
        return favoriteRepository.getAllVacancyIds()
    }
}
