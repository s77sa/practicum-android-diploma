package ru.practicum.android.diploma.domain.models

data class Filter(
    val area: String?,
    val pageLimit: Int = 20,
    val showSalary: Boolean,
    val industry: String?,
    val salary: Int?
)
