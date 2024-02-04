package ru.practicum.android.diploma.data.repository

import android.content.Context
import androidx.core.content.ContextCompat.getString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.conventers.VacancyMapper
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.dto.VacancyDetailResponse
import ru.practicum.android.diploma.data.dto.VacancyRequest
import ru.practicum.android.diploma.data.dto.VacancyResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.api.SearchRepository
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.util.Resource

const val SUCCESS = 200
const val NO_CONNECTION = -1

class SearchRepositoryImpl(
    private val networkClient: NetworkClient,
    private val context: Context,
    private val converter: VacancyMapper,
    private val appDatabase: AppDatabase
) : SearchRepository {
    override var vacancyCurrentPage: Int? = null
    override var foundItems: Int? = null
    override var pages: Int? = null
    override fun searchVacancies(text: String, filter: Filter, page: Int): Flow<Resource<List<Vacancy>>> = flow {
        val response = networkClient.doRequest(VacancyRequest(text, filter, page).map())
        when (response.resultCode) {
            NO_CONNECTION -> {
                emit(Resource.Error(getString(context, R.string.no_internet)))
            }

            SUCCESS -> {
                with(response as VacancyResponse) {
                    vacancyCurrentPage = response.page
                    foundItems = response.found
                    val data = converter.mapList(response)
                    this@SearchRepositoryImpl.pages = response.pages

                    emit(Resource.Success(data))
                }
            }

            else -> {
                emit(Resource.Error("Ошибка ${response.resultCode}"))
            }
        }
    }

    override suspend fun getDetails(id: String): Resource<Vacancy> {
        val response = networkClient.getVacancy(id)
        return when (response.resultCode) {
            NO_CONNECTION -> {
                Resource.Error(getString(context, R.string.no_internet))
            }

            SUCCESS -> {
                val favList = appDatabase.favouriteDao().getFavId()
                val vacancyResponse = response as VacancyDetailResponse
                val vacancy = converter.map(vacancyResponse, vacancyResponse.id in favList)
                Resource.Success(vacancy)
            }

            else -> {
                Resource.Error("Ошибка ${response.resultCode}")
            }
        }
    }
}
