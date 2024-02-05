package ru.practicum.android.diploma.domain.models

data class FilterSettings(
    val country: Country?,
    val areaPlain: AreaPlain?,
    val industry: Industry?,
    val plainFilterSettings: PlainFilterSettings?
)
