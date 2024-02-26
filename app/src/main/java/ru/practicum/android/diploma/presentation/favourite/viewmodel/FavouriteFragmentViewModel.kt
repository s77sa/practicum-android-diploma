package ru.practicum.android.diploma.presentation.favourite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.FavouriteInteractor
import ru.practicum.android.diploma.domain.models.FavouriteStates
import ru.practicum.android.diploma.domain.models.Vacancy

class FavouriteFragmentViewModel(
    private val favouriteInteractor: FavouriteInteractor
) : ViewModel() {

    private val stateLiveData = MutableLiveData<Pair<FavouriteStates, MutableList<Vacancy>>>()

    fun getState(): LiveData<Pair<FavouriteStates, MutableList<Vacancy>>> = stateLiveData

    fun loadFavourites() {
        viewModelScope.launch {
            favouriteInteractor.getFavourites().collect {
                stateLiveData.value = it
            }
        }
    }
}
