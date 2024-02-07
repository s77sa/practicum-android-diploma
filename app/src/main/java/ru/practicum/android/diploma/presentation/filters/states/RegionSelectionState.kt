package ru.practicum.android.diploma.presentation.filters.states

import ru.practicum.android.diploma.domain.models.Area

sealed class RegionSelectionState {
    object Loading : RegionSelectionState()
    data class Success(val selectedRegion: List<Area>?) : RegionSelectionState()
    object Error : RegionSelectionState()
    object NoInternet : RegionSelectionState()
    object NoData : RegionSelectionState()
}
