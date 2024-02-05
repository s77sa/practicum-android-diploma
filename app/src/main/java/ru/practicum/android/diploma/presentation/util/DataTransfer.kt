package ru.practicum.android.diploma.presentation.util

import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.PlainFilterSettings

object DataTransfer {

    private val TAG = DataTransfer::class.java.simpleName
    private var plainFilterSettings: PlainFilterSettings? = null
    private var country: Country? = null
    private var area: Area? = null
    private var industry: Industry? = null

    fun getIndustry(): Industry? {
        return industry
    }

    fun setIndustry(industryValue: Industry) {
        industry = industryValue
    }

    fun getArea(): Area? {
        return area
    }

    fun setArea(areaValue: Area?) {
        area = areaValue
    }

    fun getCountry(): Country? {
        return country
    }

    fun setCountry(countryValue: Country?) {
        country = countryValue
    }

    fun getPlainFilters(): PlainFilterSettings? {
        return plainFilterSettings
    }

    fun setPlainFilters(plainFilter: PlainFilterSettings?) {
        plainFilterSettings = plainFilter
    }

}
