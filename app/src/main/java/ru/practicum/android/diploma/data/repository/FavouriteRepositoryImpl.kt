package ru.practicum.android.diploma.data.repository

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.converter.VacancyConverter
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.domain.api.FavouriteRepository
import ru.practicum.android.diploma.domain.models.FavouriteStates
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.util.Resource

class FavouriteRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val favouriteConverter: VacancyConverter,
    private val context: Context
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
        try {
            val vacancyDb = appDatabase.favouriteDao().getVacancyById(vacancyId)
            return Resource.Success(favouriteConverter.map(vacancyDb))
        } catch (e: NullPointerException) {
            Log.i(RetrofitNetworkClient.TAG, "$e")
            return Resource.Error(ContextCompat.getString(context, R.string.no_internet))
        }
    }
}
