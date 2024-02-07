package ru.practicum.android.diploma.data.conventers

import ru.practicum.android.diploma.data.dto.IndustrySettingsDto
import ru.practicum.android.diploma.domain.models.Industry

object IndustrySettingsConverter {
    fun map(industry: IndustrySettingsDto): Industry {
        return Industry(
            id = industry.id,
            name = industry.name,
            isChecked = industry.isChecked
        )
    }

    fun map(industry: Industry): IndustrySettingsDto {
        return IndustrySettingsDto(
            id = industry.id,
            name = industry.name,
            isChecked = industry.isChecked
        )
    }
}
