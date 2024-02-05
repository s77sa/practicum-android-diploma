package ru.practicum.android.diploma.domain.models

data class FilterSettings(
    val country: Country?,
    val area: Area?,
    val industry: Industry?,
    val plainFilterSettings: PlainFilterSettings?
)
