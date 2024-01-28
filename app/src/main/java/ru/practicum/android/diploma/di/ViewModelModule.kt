package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.data.sharing.ExternalNavigatorRepositoryImpl
import ru.practicum.android.diploma.domain.api.ExternalNavigatorRepository
import ru.practicum.android.diploma.domain.api.VacancyInteractor
import ru.practicum.android.diploma.domain.impl.VacancyInteractorImpl
import ru.practicum.android.diploma.presentation.favourite.viewmodel.FavouriteFragmentViewModel
import ru.practicum.android.diploma.presentation.search.viewmodel.SearchViewModel
import ru.practicum.android.diploma.presentation.viewmodel.VacancyViewModel

val viewModelModule = module {

    viewModel<SearchViewModel> {
        SearchViewModel(searchInteractor = get(), context = get())
    }

    viewModel<FavouriteFragmentViewModel> {
        FavouriteFragmentViewModel()
    }
    single<ExternalNavigatorRepository> { ExternalNavigatorRepositoryImpl(get()) }
    single<VacancyInteractor> { VacancyInteractorImpl(get(), get()) }
    viewModel<VacancyViewModel> {
        VacancyViewModel(interactor = get())
    }
}
