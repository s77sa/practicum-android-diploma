package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.RegionRepository
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.presentation.util.Resource

class RegionRepositoryImpl : RegionRepository {
    override fun getRegions(countryId: String): Flow<Resource<List<Region>>> {
        TODO("Not yet implemented")
    }

    override fun applyRegionFilter(region: Region) {
        TODO("Not yet implemented")
    }

    override fun searchRegionByName(regionName: String): Flow<Resource<List<Region>>> {
        TODO("Not yet implemented")
    }

    override fun getSelectedRegion(): Region {
        TODO("Not yet implemented")
    }

    override fun clearRegionFilter() {
        TODO("Not yet implemented")
    }

}

