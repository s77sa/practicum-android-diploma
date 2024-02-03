package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.FilterInteractor
import ru.practicum.android.diploma.domain.api.FilterRepository
import ru.practicum.android.diploma.domain.models.Filter

class FilterInteractorImpl(private val filterRepository: FilterRepository) : FilterInteractor {
    override fun load(): Filter? {
        return filterRepository.load()
    }

    override fun write(filter: Filter) {
        filterRepository.write(filter)
    }
}
