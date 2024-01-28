package ru.practicum.android.diploma.presentation.favourite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.feature.favourite.domain.usecase.GetAllVacancyUseCase
import ru.practicum.android.diploma.presentation.favourite.FavouriteVacancyState

class FavouriteFragmentViewModel(
    private val getAllVacancyUseCase: GetAllVacancyUseCase
) :
    ViewModel() {

    private val _state = MutableStateFlow<FavouriteVacancyState>(FavouriteVacancyState.Empty)
    val state: StateFlow<FavouriteVacancyState> = _state

    init {
        getAllVacancies()
    }

    fun getAllVacancies() {
        viewModelScope.launch {
            getAllVacancyUseCase().collect { vacancy ->
                if (vacancy.isNotEmpty()) {
                    _state.value = FavouriteVacancyState.VacancyLoaded(vacancy)
                } else {
                    _state.value = FavouriteVacancyState.Empty
                }
            }
        }
    }
}

