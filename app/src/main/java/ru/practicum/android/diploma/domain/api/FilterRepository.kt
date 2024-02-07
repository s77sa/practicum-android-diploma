package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.models.FilterSettings

interface FilterRepository {
    fun load(): Filter?
    fun write(filter: Filter)
    fun loadFilterSettings(): FilterSettings?
    fun writeFilterSettings(filter: FilterSettings)
}
