package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.FavouriteInteractor
import ru.practicum.android.diploma.domain.api.FavouriteRepository
import ru.practicum.android.diploma.domain.models.FavouriteStates
import ru.practicum.android.diploma.domain.models.Vacancy

class FavouriteInteractorImpl(
    private val favouriteRepository: FavouriteRepository,
) : FavouriteInteractor {
    override suspend fun addFavourite(vacancy: Vacancy) {
        favouriteRepository.addFavourite(vacancy)
    }

    override suspend fun deleteFavourite(vacancy: Vacancy) {
        favouriteRepository.deleteFavourite(vacancy)
    }

    override fun getFavourites(): Flow<Pair<FavouriteStates, MutableList<Vacancy>>> {
        return favouriteRepository.getFavourites()
    }

    override fun getFavourite(vacancyId: String): Flow<List<Vacancy>> {
        return favouriteRepository.getFavourite(vacancyId)
    }
}

