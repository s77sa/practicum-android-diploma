package ru.practicum.android.diploma.data.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.practicum.android.diploma.data.dto.FilterDTO
import ru.practicum.android.diploma.domain.api.FilterRepository
import ru.practicum.android.diploma.domain.models.Filter

const val FILTER_STORAGE = "filter_storage"
const val FILTER_KEY = "filter_key"

class FilterRepositoryImpl(val sharedPreferences: SharedPreferences) : FilterRepository {
    override fun load(): Filter? {
        val json = sharedPreferences.getString(FILTER_KEY, null)
        val filterDto = Gson().fromJson(json, FilterDTO::class.java)
        return Filter(
            area = filterDto.area,
            pageLimit = filterDto.pageLimit,
            industry = filterDto.industry,
            showSalary = filterDto.showSalary,
            salary = filterDto.salary
        )
    }

    override fun write(filter: Filter) {
        val filterDto = Filter(
            area = filter.area,
            pageLimit = filter.pageLimit,
            industry = filter.industry,
            showSalary = filter.showSalary,
            salary = filter.salary
        )
        val json = Gson().toJson(filterDto)
        sharedPreferences.edit()
            .putString(FILTER_KEY, json)
            .apply()
    }
}
