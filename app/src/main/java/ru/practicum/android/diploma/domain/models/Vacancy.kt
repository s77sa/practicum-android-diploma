package ru.practicum.android.diploma.domain.models

data class Vacancy(
    val id: String,
    val name: String,
    val city: String,
    val employer: String?,
    val employerLogoUrl: String?,
    val department: String?,
    val salaryCurrency: String?,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val contactEmail: String?,
    val contactName: String?,
    val contactPhones: List<String>,
    val contactComment: List<String?>?,
    val description: String,
    val url: String?,
    val area: String,
    val logo: String?,
    val experience: String?,
    val skills: List<String>?,
    val schedule: String?,
    val isFavourite: Boolean?,
    val address: String?
)
