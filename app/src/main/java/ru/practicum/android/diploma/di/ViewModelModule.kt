package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
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
    viewModel<VacancyViewModel>{
        VacancyViewModel()
    }
}
