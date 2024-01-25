package ru.practicum.android.diploma.data.db.converter

import ru.practicum.android.diploma.data.db.entity.FavouriteVacancyEntity
import ru.practicum.android.diploma.domain.models.Vacancy
import java.util.Calendar

class VacancyConverter {

    fun map(vacancy: Vacancy): FavouriteVacancyEntity {
        return FavouriteVacancyEntity(
            vacancyId = vacancy.id,
            name = vacancy.name,
            city = vacancy.city,
            employer = vacancy.employer,
            employerLogoUrl = vacancy.employerLogoUrl,
            department = vacancy.department,
            salaryCurrency = vacancy.salaryCurrency,
            salaryFrom = vacancy.salaryFrom,
            salaryTo = vacancy.salaryTo,
            contactEmail = vacancy.contactEmail,
            contactName = vacancy.contactName,
            contactPhones = "",
            contactComment = "",
            description = vacancy.description,
            url = "",
            area = vacancy.area,
            logo = vacancy.logo,
            experience = vacancy.experience,
            skills = "",
            schedule = vacancy.schedule,
            isFavourite = true,
            inDbDate = Calendar.getInstance().time.time
        )
    }

    fun map(vacancy: FavouriteVacancyEntity): Vacancy {
        return Vacancy(
            id = vacancy.vacancyId,
            name = vacancy.name,
            city = vacancy.city,
            employer = vacancy.employer,
            employerLogoUrl = vacancy.employerLogoUrl,
            department = vacancy.department,
            salaryCurrency = vacancy.salaryCurrency,
            salaryFrom = vacancy.salaryFrom,
            salaryTo = vacancy.salaryTo,
            contactEmail = vacancy.contactEmail,
            contactName = vacancy.contactName,
            contactPhones = emptyList(),
            contactComment = emptyList(),
            description = vacancy.description,
            url = vacancy.url,
            area = vacancy.area,
            logo = vacancy.logo,
            experience = vacancy.experience,
            skills = emptyList(),
            schedule = vacancy.schedule,
            isFavourite = vacancy.isFavourite,
        )
    }
}
