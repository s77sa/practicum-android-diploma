package ru.practicum.android.diploma.data.db.converter

import com.google.gson.Gson
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
            contactPhones = listToString(vacancy.contactPhones),
            contactComment = listToString(vacancy.contactComment),
            description = vacancy.description,
            url = vacancy.url,
            area = vacancy.area,
            logo = vacancy.logo,
            experience = vacancy.experience,
            skills = listToString(vacancy.skills),
            schedule = vacancy.schedule,
            isFavourite = true,
            address = vacancy.address,
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
            contactPhones = stringToList(vacancy.contactPhones),
            contactComment = stringToList(vacancy.contactComment),
            description = vacancy.description,
            url = vacancy.url,
            area = vacancy.area,
            logo = vacancy.logo,
            experience = vacancy.experience,
            skills = stringToList(vacancy.skills),
            schedule = vacancy.schedule,
            address = vacancy.address,
            isFavourite = vacancy.isFavourite,
        )
    }

    private fun listToString(listString: List<String?>?): String {
        return Gson().toJson(listString)
    }

    private fun stringToList(string: String?): List<String> {
        val listOfStrings = mutableListOf<String>()
        if (string != null) {
            val listFromGson = Gson().fromJson(string, Array<String>::class.java) ?: emptyArray()
            listOfStrings.addAll(listFromGson)
        } else {
            listOfStrings.addAll(emptyList())
        }
        return listOfStrings
    }
}
