package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.conventers.AreaMapper
import ru.practicum.android.diploma.data.conventers.IndustryMapper
import ru.practicum.android.diploma.data.repository.AreaRepositoryImpl
import ru.practicum.android.diploma.data.repository.IndustryRepositoryImpl
import ru.practicum.android.diploma.domain.api.AreaInteractor
import ru.practicum.android.diploma.domain.api.AreaRepository
import ru.practicum.android.diploma.domain.api.IndustryInteractor
import ru.practicum.android.diploma.domain.api.IndustryRepository
import ru.practicum.android.diploma.domain.impl.AreaInteractorImpl
import ru.practicum.android.diploma.domain.impl.IndustryInteractorImpl

val filterModule = module {

    single<IndustryInteractor> {
        IndustryInteractorImpl(repository = get())
    }

    single<IndustryRepository> {

        IndustryRepositoryImpl(networkClient = get(), context = get(), mapper = get())
    }
    factory { IndustryMapper() }

    single<AreaInteractor> {
        AreaInteractorImpl(repository = get())
    }

    single<AreaRepository> {

        AreaRepositoryImpl(networkClient = get(), context = get(), converter = get())
    }
    factory { AreaMapper() }
}
