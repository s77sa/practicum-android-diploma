package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavouriteVacancy {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteVacancy(vacancy: FavouriteVacancy)

    @Query("SELECT * FROM favourite_vacancy_table")
    suspend fun getFavouriteVacancies(): List<FavouriteVacancy>

    @Query("SELECT * FROM favourite_vacancy_table  WHERE isFavourite=1 ORDER BY inDbDate DESC")
    suspend fun getFavouriteVacanciesByTime(): List<FavouriteVacancy>

    @Query("SELECT * FROM favourite_vacancy_table WHERE isFavourite=1 AND vacancyId = :vacancyId")
    suspend fun getFavoriteTrack(vacancyId: String): List<FavouriteVacancy>

    @Query("DELETE FROM favourite_vacancy_table WHERE isFavourite=1 AND vacancyId = :vacancyId")
    suspend fun deleteFavoriteVacancy(vacancyId: String)
}
