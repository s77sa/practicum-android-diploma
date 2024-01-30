package ru.practicum.android.diploma.domain.models

sealed interface FavouriteStates {
    object Empty : FavouriteStates
    object Error : FavouriteStates
    object Success : FavouriteStates
}
