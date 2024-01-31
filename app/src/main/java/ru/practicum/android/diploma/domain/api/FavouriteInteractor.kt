package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.FavouriteStates
import ru.practicum.android.diploma.domain.models.Vacancy

interface FavouriteInteractor {
    suspend fun addFavourite(vacancy: Vacancy)
    suspend fun deleteFavourite(vacancy: Vacancy)
    fun getFavourites(): Flow<Pair<FavouriteStates, MutableList<Vacancy>>>
    fun getFavourite(vacancyId: String): Flow<List<Vacancy>>
    suspend fun getFavId(): List<String>
}
