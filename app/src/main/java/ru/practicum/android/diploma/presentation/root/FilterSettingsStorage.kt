package ru.practicum.android.diploma.presentation.root

import ru.practicum.android.diploma.domain.models.FilterSettings

interface FilterSettingsStorage {
    fun getFilters(): FilterSettings?

    fun setFilters(filterSettings: FilterSettings)
}
