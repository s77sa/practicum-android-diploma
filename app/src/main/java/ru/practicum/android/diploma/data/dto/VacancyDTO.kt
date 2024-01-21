package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class VacancyDTO(
    val id: String,
    val name: String,
    val url: String,
    val area: AreaDTO,
    val contacts: ContactsDTO?,
    val description: String,
    val employer: EmployerDTO?,
    val employment: EmploymentDTO?,
    val experience: ExperienceDTO?,
    @SerializedName("key_skills")
    val keySkills: List<KeySkillDTO>,
    @SerializedName("professional_roles")
    val professionalRoles: List<ProfessionalRoleDTO>?,
    val salary: SalaryDTO?,
    val schedule: ScheduleDTO?
)
