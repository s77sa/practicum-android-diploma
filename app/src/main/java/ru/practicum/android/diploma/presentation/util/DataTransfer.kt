package ru.practicum.android.diploma.presentation.util

import android.util.Log
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

    fun setIndustry(industryValue: Industry?) {
        industry = industryValue
        Log.i(TAG, " Установлен industry=${industry?.name}")
    }

    fun getArea(): Area? {
        return area
    }

    fun setArea(areaValue: Area?) {
        area = areaValue
        Log.i(TAG, " Установлен area=${area?.name}")
    }

    fun getCountry(): Country? {
        return country
    }

    fun setCountry(countryValue: Country?) {
        country = countryValue
        Log.i(TAG, " Установлен country=${country?.name}")
    }

    fun getPlainFilters(): PlainFilterSettings? {
        return plainFilterSettings
    }

    fun setPlainFilters(plainFilter: PlainFilterSettings?) {
        plainFilterSettings = plainFilter
        Log.i(TAG, " Установлен industry=${plainFilterSettings?.expectedSalary}")
    }
}
