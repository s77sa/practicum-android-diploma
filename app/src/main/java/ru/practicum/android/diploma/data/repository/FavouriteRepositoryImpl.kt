package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.converter.VacancyConverter
import ru.practicum.android.diploma.domain.api.FavouriteRepository
import ru.practicum.android.diploma.domain.models.FavouriteStates
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.util.Resource

class FavouriteRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val favouriteConverter: VacancyConverter,

    ) : FavouriteRepository {
    override suspend fun addFavourite(vacancy: Vacancy) {
        appDatabase.favouriteDao().addFavourite(favouriteConverter.map(vacancy))
    }

    override suspend fun deleteFavourite(vacancy: Vacancy) {
        appDatabase.favouriteDao().deleteFavourite(favouriteConverter.map(vacancy))
    }

    override fun getFavourites(): Flow<Pair<FavouriteStates, MutableList<Vacancy>>> = flow {
        // try {
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
    }

    override fun getFavourite(vacancyId: String): Flow<List<Vacancy>> = flow {
        val vacancy = appDatabase.favouriteDao().getFavourite(vacancyId)
        emit(vacancy.map { vacancy ->
            favouriteConverter.map(vacancy)
        })
    }

    override suspend fun getFavId(): List<String> {
        return appDatabase.favouriteDao().getFavId()
    }

    override suspend fun getDbDetailById(vacancyId: String): Resource<Vacancy> {
        return if (appDatabase.favouriteDao().getVacancyById(vacancyId) != null) {
            val vacancyDb = appDatabase.favouriteDao().getVacancyById(vacancyId)
            Resource.Success(favouriteConverter.map(vacancyDb))
        } else {
            Resource.Error("No internet connection")
        }
    }
}
