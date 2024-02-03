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
            }
        }
        val nestedAreasFlat = response.items.map { it.areas!! }.flatten()
        nestedCities.addAll(nestedAreasFlat)
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
        val responseItemsRegions = response.items.map { area -> area.areas!! }.flatten()
        val nestedCities =
            responseItemsRegions.map { area -> area.areas!!.map { it.copy(parentId = area.parentId) } }.flatten()
        val nestedRegions = mutableListOf<Area>()
        for (region in responseItemsRegions) {
            if (region.areas?.isEmpty() == true) {
                nestedRegions.add(
                    Area(
                        id = region.id,
                        name = region.name,
                        parentId = region.parentId,
                        countryName = response.items.filter { it.id == region.parentId }[0].name
                    )
                )
            }
        }
        nestedRegions.addAll(nestedCities.map { city ->
            Area(
                id = city.id,
                name = city.name,
                parentId = city.parentId,
                countryName = response.items.filter { it.id == city.parentId }[0].name
            )
        })
        return nestedRegions

    }

}
