package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.dto.VacancyResponse


class RetrofitNetworkClient(
    private val context: Context,
    private val hhapi: HHApi,
) : NetworkClient {
    override suspend fun doRequest(request: Map<String, String>): VacancyResponse {
        if (!isConnected()) {
            return VacancyResponse(items = emptyList()).apply { code = -1 }
        }

        return withContext(Dispatchers.IO) {
            val response = hhapi.search(request)

            when (response.isSuccessful) {
                true -> VacancyResponse(items = response.body()?.items).apply { code = response.code() }
                else -> {
                    VacancyResponse(items = emptyList()).apply { code = response.code() }
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
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}
