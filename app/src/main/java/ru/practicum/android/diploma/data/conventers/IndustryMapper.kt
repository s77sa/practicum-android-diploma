package ru.practicum.android.diploma.data.conventers

import ru.practicum.android.diploma.data.dto.IndustryDto
import ru.practicum.android.diploma.domain.models.Industry

class IndustryMapper {
    fun map(industries: List<IndustryDto>): List<Industry> {
        val allCategories = mutableListOf<Industry>()
        val categories = industries.map {
            Industry(
                id = it.id,
                name = it.name,
                isChecked = false
            )
        }
        val subCategoriesList = mutableListOf<IndustryDto>()
        for (category in industries) {
            for (industry in category.industries!!) {
                subCategoriesList.add(industry)
            }
        }
        val subCategories = subCategoriesList.map {
            Industry(
                id = it.id,
                name = it.name,
                isChecked = false
            )
        }
        allCategories.addAll(categories)
        allCategories.addAll(subCategories)
        return allCategories
    }
}
