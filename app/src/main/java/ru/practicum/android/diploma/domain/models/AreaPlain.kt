package ru.practicum.android.diploma.domain.models

data class AreaPlain(
    val id: String,
    val name: String,
    val parentId: String? = null
)
