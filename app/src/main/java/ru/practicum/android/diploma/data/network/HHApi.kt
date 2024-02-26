package ru.practicum.android.diploma.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.dto.AreaNestedDto
import ru.practicum.android.diploma.data.dto.IndustryDto
import ru.practicum.android.diploma.data.dto.VacancyDetailResponse
import ru.practicum.android.diploma.data.dto.VacancyResponse

const val ACCESS_TOKEN = BuildConfig.HH_ACCESS_TOKEN

interface HHApi {
    @Headers(
        "Authorization: Bearer $ACCESS_TOKEN",
        "HH-User-Agent: HHPracticum (px316@yandex.ru)"
    )
    @GET("/vacancies/")
    suspend fun search(
        @QueryMap params: Map<String, String>
    ): Response<VacancyResponse>

    @Headers(
        "Authorization: Bearer $ACCESS_TOKEN",
        "HH-User-Agent: HHPracticum (px316@yandex.ru)"
    )
    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancy(@Path("vacancy_id") id: String): Response<VacancyDetailResponse>

    @GET("/areas/")
    suspend fun getArea(): Response<List<AreaNestedDto>>

    @GET("/areas/{area_id}/")
    suspend fun getNestedArea(@Path("area_id") id: String): Response<AreaNestedDto>

    @GET("/industries/")
    suspend fun getIndustries(): Response<List<IndustryDto>>

}
