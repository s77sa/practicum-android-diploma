package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.dto.AreaResponse
import ru.practicum.android.diploma.data.dto.IndustryResponse
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.VacancyDetailResponse
import ru.practicum.android.diploma.data.dto.VacancyResponse

class RetrofitNetworkClient(
    private val context: Context,
    private val hhApi: HHApi,
) : NetworkClient {
    override suspend fun doRequest(request: Map<String, String>): Response {
        if (! isConnected()) {
            return Response().apply { resultCode = - 1 }
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = hhApi.search(request)

                when (response.isSuccessful) {
                    true -> VacancyResponse(
                        items = response.body()?.items,
                        found = response.body()?.found,
                        page = response.body()?.page,
                        pages = response.body()?.pages
                    ).apply { if (isConnected()) resultCode = response.code() }

                    else -> {
                        Response().apply { resultCode = response.code() }
                    }
                }
            } catch (e: Error) {
                Response().apply { resultCode = - 1 }
            }
        }
    }

    override suspend fun getVacancy(id: String): Response {
        if (! isConnected()) {
            return Response().apply { resultCode = - 1 }
        }
        return withContext(Dispatchers.IO) {
            val response = hhApi.getVacancy(id)

            when (response.isSuccessful) {
                true -> {
                    val responseReturn = response.body() as VacancyDetailResponse
                    responseReturn.apply { resultCode = response.code() }
                }

                else -> {
                    Response().apply { resultCode = response.code() }
                }
            }
        }
    }

    override suspend fun getAreas(): Response {
        if (! isConnected()) {
            return Response().apply { resultCode = - 1 }
        }
        return withContext(Dispatchers.IO) {
            val response = hhApi.getArea()

            when (response.isSuccessful) {
                true -> {
                    AreaResponse().apply {
                        resultCode = response.code()
                        items = response.body() !!
                    }
                }

                else -> {
                    Response().apply { resultCode = response.code() }
                }
            }
        }
    }

    override suspend fun getNestedAreas(id: String): Response {
        if (! isConnected()) {
            return Response().apply { resultCode = - 1 }
        }
        return withContext(Dispatchers.IO) {
            val response = hhApi.getNestedArea(id)
//            val responseBody = response.body()?.areas?.map { it.areas }

            when (response.isSuccessful) {
                true -> {
                    AreaResponse().apply {
                        resultCode = response.code()
                        items = response.body()?.areas !!
                    }
                }

                else -> {
                    Response().apply { resultCode = response.code() }
                }
            }
        }
    }

    override suspend fun getIndustries(): Response {
        if (! isConnected()) {
            return Response().apply { resultCode = - 1 }
        }
        return withContext(Dispatchers.IO) {
            val response = hhApi.getIndustries()

            when (response.isSuccessful) {
                true -> {
                    IndustryResponse().apply {
                        resultCode = response.code()
                        items = response.body() !!
                    }
                }

                else -> {
                    Response().apply { resultCode = response.code() }
                }
            }
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) or
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) or
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}
