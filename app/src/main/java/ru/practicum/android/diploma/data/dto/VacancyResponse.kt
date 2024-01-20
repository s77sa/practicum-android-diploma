package ru.practicum.android.diploma.data.dto

data class VacancyResponse(
    var code: Int = 0, val items: List<VacancyDTO>?
)
