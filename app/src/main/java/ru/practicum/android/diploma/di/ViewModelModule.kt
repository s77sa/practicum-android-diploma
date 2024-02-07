package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.data.sharing.ExternalNavigatorRepositoryImpl
import ru.practicum.android.diploma.domain.api.ExternalNavigatorRepository
import ru.practicum.android.diploma.domain.api.VacancyInteractor
import ru.practicum.android.diploma.domain.impl.VacancyInteractorImpl
import ru.practicum.android.diploma.presentation.favourite.viewmodel.FavouriteFragmentViewModel
import ru.practicum.android.diploma.presentation.filters.viewmodel.FiltersSettingsViewModel
import ru.practicum.android.diploma.presentation.filters.viewmodel.SelectCountryViewModel
import ru.practicum.android.diploma.presentation.filters.viewmodel.SelectIndustryViewModel
import ru.practicum.android.diploma.presentation.filters.viewmodel.SelectRegionViewModel
import ru.practicum.android.diploma.presentation.filters.viewmodel.SelectWorkplaceViewModel
import ru.practicum.android.diploma.presentation.search.viewmodel.SearchViewModel
import ru.practicum.android.diploma.presentation.util.DataTransfer
import ru.practicum.android.diploma.presentation.vacancy.viewmodel.VacancyViewModel

val viewModelModule = module {

    viewModel<SearchViewModel> {
        SearchViewModel(get(), get())
    }

    viewModel<FavouriteFragmentViewModel> {
        FavouriteFragmentViewModel(get())
    }

    viewModel<FiltersSettingsViewModel> {
        FiltersSettingsViewModel(get())
    }

    viewModel<SelectCountryViewModel> {
        SelectCountryViewModel(get())
    }

    viewModel<SelectIndustryViewModel> {
        SelectIndustryViewModel(get(), get(), dataTransfer = get())
    }

    viewModel<SelectRegionViewModel> {
        SelectRegionViewModel(get(), get())
    }

    viewModel<SelectWorkplaceViewModel> {
        SelectWorkplaceViewModel(get(), get())
    }

    single<ExternalNavigatorRepository> { ExternalNavigatorRepositoryImpl(get()) }
    single<VacancyInteractor> { VacancyInteractorImpl(get(), get(), get()) }
    viewModel<VacancyViewModel> {
        VacancyViewModel(vacancyInteractor = get(), favouriteInteractor = get())
    }

    single { DataTransfer }
}
