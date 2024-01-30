package ru.practicum.android.diploma.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.repository.FavouriteRepositoryImpl
import ru.practicum.android.diploma.domain.api.FavouriteInteractor
import ru.practicum.android.diploma.domain.api.FavouriteRepository
import ru.practicum.android.diploma.domain.impl.FavouriteInteractorImpl
import ru.practicum.android.diploma.presentation.favourite.viewmodel.FavouriteFragmentViewModel

val favouriteModule = module {

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    single<FavouriteRepository> {
        FavouriteRepositoryImpl(get(), get())
    }

    single<FavouriteInteractor> {
        FavouriteInteractorImpl(get())
    }

    viewModel {
        FavouriteFragmentViewModel(get())
    }
}
