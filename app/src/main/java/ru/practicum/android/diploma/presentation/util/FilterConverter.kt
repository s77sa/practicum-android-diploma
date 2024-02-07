package ru.practicum.android.diploma.presentation.util

import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.models.FilterSettings

object FilterConverter {
    fun convert(filterSettings: FilterSettings): Filter? {
        return filterSettings.plainFilterSettings?.notShowWithoutSalary?.let {
            Filter(
                area = filterSettings.country?.id,
                showSalary = it,
                industry = filterSettings.industry?.id,
                salary = filterSettings.plainFilterSettings.expectedSalary

            )
        }
    }
}
