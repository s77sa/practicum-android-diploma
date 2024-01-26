package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.practicum.android.diploma.data.db.entity.FavouriteVacancyEntity

@Dao
interface FavouriteVacancy {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteVacancy(vacancy: FavouriteVacancyEntity)

    @Query("SELECT * FROM favourite_vacancy_table")
    suspend fun getFavouriteVacancies(): List<FavouriteVacancyEntity>

    @Query("SELECT * FROM favourite_vacancy_table  WHERE isFavourite=1 ORDER BY inDbDate DESC")
    suspend fun getFavouriteVacanciesByTime(): List<FavouriteVacancyEntity>

    @Query("SELECT * FROM favourite_vacancy_table WHERE isFavourite=1 AND vacancyId = :vacancyId")
    suspend fun getFavoriteVacancy(vacancyId: String): List<FavouriteVacancyEntity>

    @Query("DELETE FROM favourite_vacancy_table WHERE isFavourite=1 AND vacancyId = :vacancyId")
    suspend fun deleteFavoriteVacancy(vacancyId: String)
}
