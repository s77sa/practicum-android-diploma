package ru.practicum.android.diploma.domain.models

import ru.practicum.android.diploma.data.dto.VacancyDetailResponse

sealed class FavoriteVacancyState {
    data object Empty : FavoriteVacancyState()
    data class VacancyLoaded(val vacancy: List<VacancyDetailResponse>) : FavoriteVacancyState()
}
