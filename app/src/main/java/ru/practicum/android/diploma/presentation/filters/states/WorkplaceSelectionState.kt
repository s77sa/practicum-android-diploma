package ru.practicum.android.diploma.presentation.filters.states

import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.Region

sealed class WorkplaceSelectionState {
    object Empty : WorkplaceSelectionState()
    data class CountryFilled(val country: Country) : WorkplaceSelectionState()
    data class RegionFilled(val region: Region) : WorkplaceSelectionState()
}
