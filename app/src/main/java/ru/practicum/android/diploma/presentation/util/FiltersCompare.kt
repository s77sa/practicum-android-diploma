package ru.practicum.android.diploma.presentation.util

import ru.practicum.android.diploma.domain.models.Filter

object FiltersCompare {

    // метод получает новые данные фильтра, и сохраненный фильтр
    // и возвращает false - если изменений нет и true - если имеются отличия
    fun compareFilters(newFilter: Filter, savedFilter: Filter): Boolean {
        return !(newFilter.area.equals(savedFilter.area) ||
            newFilter.showSalary == savedFilter.showSalary ||
            newFilter.salary == savedFilter.salary ||
            newFilter.industry.equals(savedFilter.industry))
    }
}
