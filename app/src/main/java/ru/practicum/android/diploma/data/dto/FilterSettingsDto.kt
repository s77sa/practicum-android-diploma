package ru.practicum.android.diploma.data.dto

data class FilterSettingsDto(
    val country: CountryDto?,
    val area: AreaSettingsDto?,
    val industry: IndustrySettingsDto?,
    val plainFilterSettings: PlainFilterDto?
)
