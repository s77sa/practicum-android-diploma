package ru.practicum.android.diploma.presentation.util

import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.models.FilterSettings

object FiltersCompare {

    // метод получает новые данные фильтра, и сохраненный фильтр
    // и возвращает false - если изменений нет и true - если имеются отличия
    fun compareFilters(newFilter: Filter, savedFilter: Filter): Boolean {
        return !(newFilter.area.equals(savedFilter.area) ||
            newFilter.showSalary == savedFilter.showSalary ||
            newFilter.salary == savedFilter.salary ||
            newFilter.industry.equals(savedFilter.industry))
    }

    fun compareFilterSettings(newFilter: FilterSettings, savedFilter: FilterSettings): Boolean {
        var result = false
        if (newFilter.plainFilterSettings?.notShowWithoutSalary !=
            savedFilter.plainFilterSettings?.notShowWithoutSalary
        ) {
            result = true
        }
        if (newFilter.plainFilterSettings?.expectedSalary != savedFilter.plainFilterSettings?.expectedSalary) {
            result = true
        }
        if (newFilter.area?.id != savedFilter.area?.id) {
            result = true
        }
        if (newFilter.industry?.id != savedFilter.industry?.id) {
            result = true
        }
        if (newFilter.country?.id != savedFilter.country?.id) {
            result = true
        }
        return result
    }
}
