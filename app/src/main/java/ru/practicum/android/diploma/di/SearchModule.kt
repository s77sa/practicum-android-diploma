package ru.practicum.android.diploma.di

import com.google.gson.Gson
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.conventers.VacancyMapper
import ru.practicum.android.diploma.data.network.HHApi
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.data.repository.SearchRepositoryImpl
import ru.practicum.android.diploma.domain.api.SearchInteractor
import ru.practicum.android.diploma.domain.api.SearchRepository
import ru.practicum.android.diploma.domain.impl.SearchInteractorImpl

val searchModule = module {

    single<HHApi> {
        Retrofit.Builder()
            .baseUrl("https://api.hh.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HHApi::class.java)
    }
    factory { Gson() }

    single<NetworkClient> {
        RetrofitNetworkClient(context = get(), hhApi = get())
    }

    single<SearchRepository> {
        SearchRepositoryImpl(networkClient = get(), context = get(), converter = get())
    }

    single<SearchInteractor> {
        SearchInteractorImpl(repository = get())
    }

    factory { VacancyMapper(appDatabase = get()) }
}
