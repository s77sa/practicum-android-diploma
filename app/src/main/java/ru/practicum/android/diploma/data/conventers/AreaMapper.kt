package ru.practicum.android.diploma.data.conventers

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
                country = null
            )
        }
        return data

    }

    fun mapCity(response: AreaResponse): List<Area> {
        val responseItems = response.items
        val allRegions = mutableListOf<Area>()
        val nestedRegions = responseItems.map {
            Area(
                id = it.id,
                name = it.name,
                parentId = it.parentId,
                country = null
            )
        }
        val nestedCities = response.items.map { it.areas!! }.flatten().map {
            Area(
                id = it.id,
                name = it.name,
                parentId = it.parentId,
                country = null
            )
        }
        allRegions.addAll(nestedRegions)
        allRegions.addAll(nestedCities)

        return allRegions
    }

    fun mapCityAll(response: AreaResponse): List<Area> {
        val responseItemsRegions = response.items.map { area -> area.areas!! }.flatten()
        val countries = response.items.map {
            Area(
                id = it.id,
                name = it.name,
                parentId = it.parentId,
                country = null
            )
        }
        val regions =
            responseItemsRegions.map { region ->
                Area(
                    id = region.id,
                    name = region.name,
                    parentId = region.parentId,
                    country = countries.filter { it.id == region.parentId }[0]
                )
            }
        val cities = responseItemsRegions.map { area -> area.areas!! }.flatten().map { city ->
            Area(
                id = city.id,
                name = city.name,
                parentId = city.parentId,
                country = regions.filter { it.id == city.parentId }[0].country
            )
        }
        val nestedRegions = mutableListOf<Area>()
        nestedRegions.addAll(regions)
        nestedRegions.addAll(cities)

        return nestedRegions
    }
}
