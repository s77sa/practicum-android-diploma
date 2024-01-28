package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.repository.SearchRepositoryImpl
import ru.practicum.android.diploma.domain.api.SearchRepository
import ru.practicum.android.diploma.data.repository.FavoriteRepositoryImpl
import ru.practicum.android.diploma.domain.api.FavouriteRepository

val repositoryModule = module {

    single<SearchRepository> {
        SearchRepositoryImpl(get(), get(), get())
    }

    single<FavouriteRepository> {
        FavoriteRepositoryImpl(get(), get())
    }

}
