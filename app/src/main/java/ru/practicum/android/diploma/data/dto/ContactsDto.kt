package ru.practicum.android.diploma.data.dto

data class ContactsDto(
    val email: String?,
    val name: String?,
    val phones: List<PhonesDto>?= null
)
