package ru.practicum.android.diploma.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.data.db.dao.FavouriteDao
import ru.practicum.android.diploma.data.db.entity.FavouriteVacancyEntity

@Database(version = 2, entities = [FavouriteVacancyEntity::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun favouriteDao(): FavouriteDao
}
