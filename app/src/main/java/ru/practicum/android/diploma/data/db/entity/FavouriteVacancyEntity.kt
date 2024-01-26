package ru.practicum.android.diploma.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

@Entity(tableName = "favourite_vacancy_table")
data class FavouriteVacancyEntity(
    @PrimaryKey
    val vacancyId: String,
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
    val contactPhones: String?,
    val contactComment: String?,
    val description: String,
    val url: String,
    val area: String,
    val logo: String?,
    val experience: String?,
    val skills: String,
    val schedule: String?,
    val isFavourite: Boolean = false,
    val inDbDate: Long = Calendar.getInstance().time.time
)
