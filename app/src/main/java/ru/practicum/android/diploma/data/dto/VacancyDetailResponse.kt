package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class VacancyDetailResponse(
    val id: String,
    val name: String,
    val url: String,
    val area: AreaDto,
    val contacts: ContactsDto?,
    val description: String,
    val employer: EmployerDto?,
    val employment: EmploymentDto?,
    val experience: ExperienceDto?,
    @SerializedName("key_skills")
    val keySkills: List<KeySkillDto>,
    @SerializedName("professional_roles")
    val professionalRoles: List<ProfessionalRoleDto>?,
    val salary: SalaryDto?,
    val schedule: ScheduleDto?
) : Response()
