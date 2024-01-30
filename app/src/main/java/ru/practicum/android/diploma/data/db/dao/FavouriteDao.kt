package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.practicum.android.diploma.data.db.entity.FavouriteVacancyEntity

@Dao
interface FavouriteDao {

    @Insert(entity = FavouriteVacancyEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavourite(vacancy: FavouriteVacancyEntity)

    @Query("DELETE FROM favourite_vacancy_table WHERE isFavourite=1 AND vacancyId = :vacancyId")
    suspend fun deleteFavourite(vacancyId: String)

    @Query("SELECT * FROM favourite_vacancy_table")
    suspend fun getFavourites(): List<FavouriteVacancyEntity>

    @Query("SELECT * FROM favourite_vacancy_table WHERE isFavourite=1 AND vacancyId = :vacancyId")
    suspend fun getFavourite(vacancyId: String): List<FavouriteVacancyEntity>
}
