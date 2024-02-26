package ru.practicum.android.diploma.data.conventers

import ru.practicum.android.diploma.data.dto.PlainFilterDto
import ru.practicum.android.diploma.domain.models.PlainFilterSettings

object PlainFilterConverter {
    fun map(plain: PlainFilterDto): PlainFilterSettings {
        return PlainFilterSettings(
            expectedSalary = plain.expectedSalary,
            notShowWithoutSalary = plain.notShowWithoutSalary
        )
    }

    fun map(plain: PlainFilterSettings): PlainFilterDto {
        return PlainFilterDto(
            expectedSalary = plain.expectedSalary,
            notShowWithoutSalary = plain.notShowWithoutSalary
        )
    }
}
