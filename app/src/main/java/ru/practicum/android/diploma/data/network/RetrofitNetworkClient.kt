package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.dto.VacancyResponse

class RetrofitNetworkClient(
    private val context: Context,
    private val hhApi: HHApi,
) : NetworkClient {
    override suspend fun doRequest(request: Map<String, String>): VacancyResponse {
        if (!isConnected()) {
            return VacancyResponse(items = emptyList()).apply { code = -1 }
        }
        return withContext(Dispatchers.IO) {
            val response = hhApi.search(request)

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
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) or
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) or
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}