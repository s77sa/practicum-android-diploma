package ru.practicum.android.diploma.data.conventers

import ru.practicum.android.diploma.data.dto.AreaNestedDto
import ru.practicum.android.diploma.data.dto.AreaResponse
import ru.practicum.android.diploma.domain.models.Area

class AreaMapper {
    fun map(response: AreaResponse): List<Area> {
        val responseItems = response.items
        val data = responseItems.map {
            Area(
                id = it.id,
                name = it.name,
                parentId = it.parentId,
                countryName = ""
            )
        }
        return data

    }

    fun mapCity(response: AreaResponse): List<Area> {
        val responseItems = response.items
        val nestedCities = mutableListOf<AreaNestedDto>()
        for (area in responseItems) {
            if (area.areas?.isEmpty() == true) {
                nestedCities.add(area)
            } else {
                val regions = area.areas
                if (regions != null) {
                    for (region in regions) {
                        nestedCities.add(region)
                    }
                }
            }

        }
        val data = nestedCities.map {
            Area(
                id = it.id,
                name = it.name,
                parentId = it.parentId,
                countryName = ""
            )
        }
        return data

    }

    fun mapCityAll(response: AreaResponse): List<Area> {
        val responseItems = response.items
        val nestedRegions = mutableListOf<Area>()
        for (country in responseItems) {
            val countryName = country.name
            for (region in country.areas!!) {
                if (region.areas?.isEmpty() == true) {
                    nestedRegions.add(
                        Area(
                            id = region.id,
                            name = region.name,
                            parentId = region.parentId,
                            countryName = countryName
                        )
                    )
                } else {
                    for (area in region.areas!!) {
                        nestedRegions.add(
                            Area(
                                id = area.id,
                                name = area.name,
                                parentId = area.parentId,
                                countryName = countryName
                            )
                        )
                    }


                }
            }
        }
        return nestedRegions

    }
}
