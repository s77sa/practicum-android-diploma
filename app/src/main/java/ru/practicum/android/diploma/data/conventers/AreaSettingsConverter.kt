package ru.practicum.android.diploma.data.conventers

import ru.practicum.android.diploma.data.dto.AreaSettingsDto
import ru.practicum.android.diploma.domain.models.Area

object AreaSettingsConverter {
    fun map(area: AreaSettingsDto): Area {
        return Area(
            id = area.id,
            name = area.name,
            parentId = area.name,
            country = map(area)
        )
    }

    fun map(area: Area): AreaSettingsDto {
        return AreaSettingsDto(
            id = area.id,
            name = area.name,
            parentId = area.parentId,
            country = map(area)
        )
    }
}
