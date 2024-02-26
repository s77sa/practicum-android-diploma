package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.IndustryInteractor
import ru.practicum.android.diploma.domain.api.IndustryRepository
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.presentation.util.Resource

class IndustryInteractorImpl(private val repository: IndustryRepository) : IndustryInteractor {
    override suspend fun getIndustries(): Pair<List<Industry>?, String?> {
        val result = repository.getIndustries()
        return if (result is Resource.Success) {
            Pair(result.data, null)
        } else {
            Pair(null, result.message)
        }
    }
}
