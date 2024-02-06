package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.RegionRepository
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.presentation.util.Resource

class RegionRepositoryImpl : RegionRepository {
    private val notImplementedMessage = "Not yet implemented"

    override fun getRegions(countryId: String): Flow<Resource<List<Region>>> {
        TODO(notImplementedMessage)
    }

    override fun applyRegionFilter(region: Region) {
        TODO(notImplementedMessage)
    }

    override fun searchRegionByName(regionName: String): Flow<Resource<List<Region>>> {
        TODO(notImplementedMessage)
    }

    override fun getSelectedRegion(): Region {
        TODO(notImplementedMessage)
    }

    override fun clearRegionFilter() {
        TODO(notImplementedMessage)
    }
}
