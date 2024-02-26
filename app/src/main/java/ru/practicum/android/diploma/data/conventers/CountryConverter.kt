package ru.practicum.android.diploma.data.conventers

import ru.practicum.android.diploma.data.dto.CountryDto
import ru.practicum.android.diploma.domain.models.Country

object CountryConverter {
    fun map(country: CountryDto): Country {
        return Country(
            id = country.id,
            name = country.name,
        )
    }

    fun map(country: Country): CountryDto {
        return CountryDto(
            id = country.id,
            name = country.name
        )
    }
}
