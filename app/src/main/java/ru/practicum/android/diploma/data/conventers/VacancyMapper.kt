package ru.practicum.android.diploma.data.conventers

import ru.practicum.android.diploma.data.dto.PhonesDto
import ru.practicum.android.diploma.data.dto.VacancyDetailResponse
import ru.practicum.android.diploma.data.dto.VacancyResponse
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyMapper {
    fun map(response: VacancyDetailResponse, isFavourite: Boolean): Vacancy {
        return Vacancy(
            id = response.id,
            name = response.name,
            city = response.area.name,
            employer = response.employer?.name,
            employerLogoUrl = response.employer?.logoUrls?.originalSize,
            department = "",
            salaryCurrency = response.salary?.currency,
            salaryFrom = response.salary?.from,
            salaryTo = response.salary?.to,
            contactEmail = response.contacts?.email,
            contactName = response.contacts?.name,
            contactPhones = phonesMap(response.contacts?.phones),
            contactComment = contactsMap(response.contacts?.phones),
            description = response.description,
            url = response.url,
            area = response.area.name,
            logo = response.employer?.logoUrls?.smallSize,
            experience = response.experience?.name,
            skills = response.keySkills.map { it.name },
            schedule = response.schedule?.name,
            isFavourite = isFavourite,
            address = response.address?.raw
        )
    }

    fun mapList(response: VacancyResponse): List<Vacancy>? {
        val data = response.items?.map {
            Vacancy(
                id = it.id,
                name = it.name,
                city = it.area.name,
                employer = it.employer?.name,
                employerLogoUrl = it.employer?.logoUrls?.originalSize,
                department = "",
                salaryCurrency = it.salary?.currency,
                salaryFrom = it.salary?.from,
                salaryTo = it.salary?.to,
                contactEmail = it.contacts?.email,
                contactName = it.contacts?.name,
                contactPhones = emptyList(),
                contactComment = emptyList(),
                description = "",
                url = it.url,
                area = it.area.name,
                logo = it.employer?.logoUrls?.smallSize,
                experience = "",
                skills = emptyList(),
                schedule = "",
                isFavourite = false,
                address = ""
            )
        }
        return data
    }

    private fun phonesMap(phones: List<PhonesDto>?): List<String> {
        val phonesList = mutableListOf<String>()
        if (phones != null) {
            for (i in phones) {
                phonesList.add(i.formatted)
            }
        }
        return phonesList
    }

    private fun contactsMap(phones: List<PhonesDto>?): List<String> {
        val contactList = mutableListOf<String>()
        if (phones != null) {
            for (i in phones) {
                i.comment?.let { contactList.add(it) }
            }
        }
        return contactList
    }
}
