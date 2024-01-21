package ru.practicum.android.diploma.data.dto

data class ContactsDTO(
    val email:String,
    val name:String,
    val phones:List<PhonesDTO>?
)
