package ru.practicum.android.diploma.data.db.converter

import ru.practicum.android.diploma.data.db.entity.FavouriteVacancyEntity
import ru.practicum.android.diploma.data.dto.VacancyDTO
import java.util.Calendar

class VacancyConverter {

    fun map(vacancy: VacancyDTO): FavouriteVacancyEntity {
        return FavouriteVacancyEntity(
            vacancy.id,
            vacancy.name,
            city = "",
            employer = "",
            employerLogoUrl = "",
            department = "",
            salaryCurrency = "",
            salaryFrom = 0,
            salaryTo = "",
            contactEmail = "",
            contactName = "",
            contactPhones = "",
            contactComment = "",
            description = "",
            vacancy.url,
            area = "",
            logo = "",
            experience = "",
            skills = "",
            schedule = "",
            isFavourite = false,
            inDbDate = Calendar.getInstance().time.time
        )
    }

    fun map(vacancy: FavouriteVacancyEntity): VacancyDTO {
        return VacancyDTO(
            vacancy.vacancyId,
            vacancy.name,
            vacancy.url
        )
    }
}
