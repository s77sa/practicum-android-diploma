package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.FilterInteractor
import ru.practicum.android.diploma.domain.api.FilterRepository
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.presentation.util.Resource

class FilterInteractorImpl(private val filterRepository: FilterRepository) : FilterInteractor {
    override fun load(): Filter? {
        return filterRepository.load()
    }

    override fun write(filter: Filter) {
        filterRepository.write(filter)
    }

    override fun getCountries(): Flow<Resource<List<Country>>> {
        TODO(NOT_IMPLEMENTED_YET)
    }

    override fun applyCountryFilter(country: Country) {
        TODO(NOT_IMPLEMENTED_YET)
    }

    override fun getSelectedCountry(): Country {
        TODO(NOT_IMPLEMENTED_YET)
    }

    override fun clearCountryFilter() {
        TODO(NOT_IMPLEMENTED_YET)
    }

    companion object {
        private const val NOT_IMPLEMENTED_YET = "Not yet implemented"
    }
}
