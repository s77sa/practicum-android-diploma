package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.converter.VacancyConverter
import ru.practicum.android.diploma.domain.api.FavouriteRepository
import ru.practicum.android.diploma.domain.models.FavouriteStates
import ru.practicum.android.diploma.domain.models.Vacancy

class FavouriteRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val favouriteConverter: VacancyConverter,
) : FavouriteRepository {
    override suspend fun addFavourite(vacancy: Vacancy) {
        appDatabase.favouriteDao().addFavourite(favouriteConverter.map(vacancy))
    }

    override suspend fun deleteFavourite(vacancyId: String) {
        appDatabase.favouriteDao().deleteFavourite(vacancyId)
    }

    override fun getFavourites(): Flow<Pair<FavouriteStates, MutableList<Vacancy>>> = flow {
        try {
            val vacancy = appDatabase.favouriteDao().getFavourites()
            if (vacancy.isEmpty()) {
                emit(Pair(FavouriteStates.Empty, mutableListOf()))
            } else {
                val mappedFavourites = ArrayList<Vacancy>()
                vacancy.forEach {
                    mappedFavourites.add(favouriteConverter.map(it))
                }
                emit(Pair(FavouriteStates.Success, mappedFavourites.toMutableList()))
            }
        } catch (e: Exception) {
            emit(Pair(FavouriteStates.Error, mutableListOf()))
        }
    }

    override fun getFavourite(vacancyId: String): Flow<List<Vacancy>> = flow {
        val vacancy = appDatabase.favouriteDao().getFavourite(vacancyId)
        emit(vacancy.map { vacancy ->
            favouriteConverter.map(vacancy)
        })
    }
}



