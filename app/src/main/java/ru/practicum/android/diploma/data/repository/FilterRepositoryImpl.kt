package ru.practicum.android.diploma.data.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.practicum.android.diploma.data.conventers.AreaSettingsConverter
import ru.practicum.android.diploma.data.conventers.CountryConverter
import ru.practicum.android.diploma.data.conventers.IndustrySettingsConverter
import ru.practicum.android.diploma.data.conventers.PlainFilterConverter
import ru.practicum.android.diploma.data.dto.FilterDTO
import ru.practicum.android.diploma.data.dto.FilterSettingsDto
import ru.practicum.android.diploma.domain.api.FilterRepository
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.models.FilterSettings

const val FILTER_STORAGE = "filter_storage"
const val FILTER_KEY = "filter_key"
const val FILTER_SETTINGS_KEY = "filter_settings_key"

class FilterRepositoryImpl(val sharedPreferences: SharedPreferences) : FilterRepository {
    override fun load(): Filter? {
        val json = sharedPreferences.getString(FILTER_KEY, null)
        return if (json == null) {
            null
        } else {
            val filterDto = Gson().fromJson(json, FilterDTO::class.java)
            Filter(
                area = filterDto.area,
                pageLimit = filterDto.pageLimit,
                industry = filterDto.industry,
                showSalary = filterDto.showSalary,
                salary = filterDto.salary
            )
        }
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

    override fun loadFilterSettings(): FilterSettings? {
        val json = sharedPreferences.getString(FILTER_SETTINGS_KEY, null)
        return if (json == null) {
            null
        } else {
            val settings = Gson().fromJson(json, FilterSettingsDto::class.java)
            FilterSettings(
                country = settings.country?.let { CountryConverter.map(it) },
                area = settings.area?.let { AreaSettingsConverter.map(it) },
                industry = settings.industry?.let { IndustrySettingsConverter.map(it) },
                plainFilterSettings = settings.plainFilterSettings?.let { PlainFilterConverter.map(it) }
            )
        }
    }

    override fun writeFilterSettings(filter: FilterSettings) {
        val filterDto = FilterSettingsDto(
            country = filter.country?.let { CountryConverter.map(it) },
            area = filter.area?.let { AreaSettingsConverter.map(it) },
            industry = filter.industry?.let { IndustrySettingsConverter.map(it) },
            plainFilterSettings = filter.plainFilterSettings?.let { PlainFilterConverter.map(it) }
        )
        val json = Gson().toJson(filterDto)
        sharedPreferences.edit()
            .putString(FILTER_SETTINGS_KEY, json)
            .apply()
    }
}
