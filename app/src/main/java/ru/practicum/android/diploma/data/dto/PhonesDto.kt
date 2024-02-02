package ru.practicum.android.diploma.data.dto

data class PhonesDto(
    val city: String,
    val comment: String? = null,
    val country: String,
    val formatted: String,
    val number: String
)
