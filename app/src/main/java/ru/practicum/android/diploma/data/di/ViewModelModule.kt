package ru.practicum.android.diploma.data.di

import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel
import ru.practicum.android.diploma.presentation.viewmodel.FavouriteFragmentViewModel
import ru.practicum.android.diploma.presentation.viewmodel.SearchViewModel

val viewModelModule = module {

    viewModel<SearchViewModel> {
        SearchViewModel()
    }

    viewModel<FavouriteFragmentViewModel> {
        FavouriteFragmentViewModel()
    }

}
