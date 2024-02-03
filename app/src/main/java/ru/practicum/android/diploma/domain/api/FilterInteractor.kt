package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.Filter

interface FilterInteractor {
    fun load(): Filter?
    fun write(filter: Filter)
}
