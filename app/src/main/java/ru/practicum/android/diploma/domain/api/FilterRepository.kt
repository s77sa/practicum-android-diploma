package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.Filter

interface FilterRepository {
    fun load(): Filter?
    fun write(filter: Filter)
}
