package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.RegionInteractor
import ru.practicum.android.diploma.domain.api.RegionRepository
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.presentation.util.Resource

class RegionInteractorImpl(private val regionRepository: RegionRepository) : RegionInteractor {
    override fun getRegions(countryId: String): Flow<Resource<List<Region>>> {
        return regionRepository.getRegions(countryId)
    }

    override fun applyRegionFilter(region: Region) {
        return regionRepository.applyRegionFilter(region)
    }

    override fun searchRegionByName(regionName: String): Flow<Resource<List<Region>>> {
        return regionRepository.searchRegionByName(regionName)
    }

    override fun getSelectedRegion(): Region {
        return regionRepository.getSelectedRegion()
    }

    override fun clearRegionFilter() {
        return regionRepository.clearRegionFilter()
    }
}

