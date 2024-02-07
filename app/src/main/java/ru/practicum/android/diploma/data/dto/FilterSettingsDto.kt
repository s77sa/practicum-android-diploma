package ru.practicum.android.diploma.data.dto

data class FilterSettingsDto(
    val country: CountryDto?,
    val area: AreaDto?,
    val industry: IndustryDto?,
    val plainFilterSettings: PlainFilterSettingsDto?
)
