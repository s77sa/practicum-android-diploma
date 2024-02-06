package ru.practicum.android.diploma.presentation.util

import ru.practicum.android.diploma.domain.models.Filter

object FiltersCompare {

    // метод получает новые данные фильтра, и сохраненный фильтр
    // и возвращает false - если изменений нет и true - если имеются отличия
    fun compareFilters(newFilter: Filter, savedFilter: Filter) : Boolean {
        if (newFilter.area.equals(savedFilter.area)) return false
        if (newFilter.showSalary.equals(savedFilter.showSalary)) return false
        if (newFilter.salary?.equals(savedFilter.salary) == true) return false
        if (newFilter.industry.equals(savedFilter.industry)) return false
        return true
    }
}
