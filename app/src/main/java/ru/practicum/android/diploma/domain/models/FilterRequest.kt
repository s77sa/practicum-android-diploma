package ru.practicum.android.diploma.domain.models

sealed class FilterRequest {
    object Industries

    object Countries

    object Regions

    data class RegionsByCountry(val countryId: String)
}
