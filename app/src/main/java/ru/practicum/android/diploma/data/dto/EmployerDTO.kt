package ru.practicum.android.diploma.data.dto

data class EmployerDTO(
    val id: String,
    val logoUrls: List<LogoUrlDTO>?,
    val name: String,
    val url: String
)
