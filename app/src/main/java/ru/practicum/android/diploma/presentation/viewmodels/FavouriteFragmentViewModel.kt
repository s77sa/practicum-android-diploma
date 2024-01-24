package ru.practicum.android.diploma.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.FavoriteVacancyState

class FavouriteFragmentViewModel() :
    ViewModel() {

    private val _state = MutableStateFlow<FavoriteVacancyState>(FavoriteVacancyState.Empty)
    val state: StateFlow<FavoriteVacancyState> = _state

    init {
        getAllVacancies()
    }

    fun getAllVacancies() {
        viewModelScope.launch {
        }
    }
}
