package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.feature.favourite.domain.usecase.GetAllVacancyUseCase
import ru.practicum.android.diploma.presentation.favourite.viewmodel.FavouriteFragmentViewModel
import ru.practicum.android.diploma.presentation.search.viewmodel.SearchViewModel

val viewModelModule = module {
    viewModel<SearchViewModel> {
        SearchViewModel(searchInteractor = get(), context = get())
    }
    factory<FavouriteFragmentViewModel> {
        FavouriteFragmentViewModel(get())
    }
    single {
        GetAllVacancyUseCase(favouriteRepository = get())
    }
}
