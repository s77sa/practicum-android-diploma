package ru.practicum.android.diploma.data.repository

import android.content.Context
import androidx.core.content.ContextCompat.getString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.conventers.VacancyMapper
import ru.practicum.android.diploma.data.dto.VacancyDetailResponse
import ru.practicum.android.diploma.data.dto.VacancyResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.api.SearchRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.Resource

const val SUCCESS = 200
const val NO_CONNECTION = -1

class SearchRepositoryImpl(
    private val networkClient: NetworkClient,
    private val context: Context,
    private val converter: VacancyMapper
) : SearchRepository {
    private var vacancyCurrentPage: Int? = null

    override fun searchVacancies(request: Map<String, String>): Flow<Resource<List<Vacancy>>> = flow {
        val response = networkClient.doRequest(request)
        when (response.resultCode) {
            NO_CONNECTION -> {
                emit(Resource.Error(getString(context, R.string.no_internet)))
            }
            SUCCESS -> {
                with(response as VacancyResponse) {
                    vacancyCurrentPage = response.page
                    val data = items?.map {
                        Vacancy(
                            id = it.id,
                            name = it.name,
                            city = it.area.name,
                            employer = it.employer?.name,
                            employerLogoUrl = it.employer?.logoUrls?.originalSize,
                            department = "",
                            salaryCurrency = it.salary?.currency,
                            salaryFrom = it.salary?.from,
                            salaryTo = it.salary?.to,
                            contactEmail = it.contacts?.email,
                            contactName = it.contacts?.name,
                            contactPhones = emptyList(),
                            contactComment = emptyList(),
                            description = "",
                            url = it.url,
                            area = it.area.name,
                            logo = it.employer?.logoUrls?.smallSize,
                            experience = "",
                            skills = emptyList(),
                            schedule = "",
                            isFavourite = false
                        )
                    }
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
                val vacancy = converter.map(response as VacancyDetailResponse)
                Resource.Success(vacancy)
            }

            else -> {
                Resource.Error("Ошибка ${response.resultCode}")
            }
        }
    }
}
