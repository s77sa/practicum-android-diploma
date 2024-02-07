package ru.practicum.android.diploma.data.dto

data class AreaSettingsDto(
    val id: String,
    val name: String,
    val parentId: String?,
    val country: AreaSettingsDto?
)
