package ru.practicum.android.diploma.data.dto

data class EmployerDto(
    val id: String,
    val logoUrls: List<LogoUrlDto>?,
    val name: String,
    val url: String
)
