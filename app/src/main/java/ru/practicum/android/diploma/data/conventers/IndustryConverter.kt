package ru.practicum.android.diploma.data.conventers

import ru.practicum.android.diploma.data.dto.IndustryDto
import ru.practicum.android.diploma.domain.models.Industry

object IndustryConverter {
    fun map(industry: IndustryDto): Industry {
        return Industry(
            id = industry.id!!,
            name = industry.name!!,
            isChecked = false
        )
    }

    fun map(industry: Industry): IndustryDto {
        return IndustryDto(
            id = industry.id,
            name = industry.name,
            industries = null
        )
    }
}
