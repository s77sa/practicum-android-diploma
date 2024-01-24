package ru.practicum.android.diploma.data.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.viewmodel.SearchViewModel

val viewModelModule = module {

    viewModel<SearchViewModel> {
        SearchViewModel()
    }
}
