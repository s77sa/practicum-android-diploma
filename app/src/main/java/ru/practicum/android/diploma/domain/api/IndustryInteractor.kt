package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.Industry

interface IndustryInteractor {
    suspend fun getIndustries(): Pair<List<Industry>?, String?>
}
