package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.converter.VacancyConverter
import ru.practicum.android.diploma.data.db.entity.FavouriteVacancyEntity
import ru.practicum.android.diploma.domain.api.FavouriteRepository
import ru.practicum.android.diploma.domain.models.Vacancy


class FavoriteRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val vacancyConverter: VacancyConverter
) : FavouriteRepository {
    override suspend fun insertVacancy(vacancyFull: Vacancy) {
        val vacancyEntity = vacancyConverter.map(vacancyFull)
        appDatabase.favouriteVacancy().insertFavouriteVacancy(vacancyEntity)
    }

    override suspend fun deleteVacancy(vacancyFull: Vacancy) {
        val vacancyEntity = vacancyConverter.map(vacancyFull)
        appDatabase.favouriteVacancy().deleteFavoriteVacancy(vacancyEntity.vacancyId)
    }

    override fun getAllVacancy(): Flow<List<Vacancy>> = flow {
        val vacancies = appDatabase.favouriteVacancy().getAllVacancy()
        emit(convertFromVacanciesEntity(vacancies))
    }

    override fun getAllVacancyIds(): Flow<List<String>> = flow {
        val listId = appDatabase.favouriteVacancy().getAllVacancyIds()
        emit(listId)
    }

    override suspend fun getVacancyById(id: String): Vacancy? {
        val vacancyEntity = appDatabase.favouriteVacancy().getVacancyById(id)
        return vacancyEntity?.let { vacancyConverter.map(it) }
    }

    private fun convertFromVacanciesEntity(vacancies: List<FavouriteVacancyEntity>): List<Vacancy> {
        return vacancies.map { vacancyConverter.map(it) }
    }
}
