package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.FavouriteStates
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.util.Resource

interface FavouriteRepository {
    suspend fun addFavourite(vacancy: Vacancy)
    suspend fun deleteFavourite(vacancy: Vacancy)
    fun getFavourites(): Flow<Pair<FavouriteStates, MutableList<Vacancy>>>
    fun getFavourite(vacancyId: String): Flow<List<Vacancy>>
    suspend fun getFavId(): List<String>
    suspend fun getDbDetailById(vacancyId: String): Resource<Vacancy>
}
