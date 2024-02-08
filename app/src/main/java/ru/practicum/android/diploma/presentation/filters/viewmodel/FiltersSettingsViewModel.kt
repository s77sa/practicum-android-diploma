package ru.practicum.android.diploma.presentation.filters.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.api.FilterInteractor
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.FilterSettings
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.PlainFilterSettings
import ru.practicum.android.diploma.presentation.util.DataTransfer
import ru.practicum.android.diploma.presentation.util.FiltersCompare

class FiltersSettingsViewModel(
    private val filterInteractor: FilterInteractor
) : ViewModel() {

    private val _countryData = MutableLiveData<Country?>()
    val countryData get() = _countryData

    private val _industryData = MutableLiveData<Industry?>()
    val industryData get() = _industryData

    private val _areaData = MutableLiveData<Area?>()
    val areaData get() = _areaData

    private val _plainFiltersData = MutableLiveData<PlainFilterSettings?>()
    val plainFiltersData get() = _plainFiltersData

    private val _equalFilter = MutableLiveData<Boolean>(false)
    val equalFilter get() = _equalFilter

    private val _changedFilter = MutableLiveData<Boolean>(false)
    val changedFilter get() = _changedFilter

    private var filterSettings: FilterSettings? = null

    fun loadFromShared() {
        filterSettings = filterInteractor.loadFilterSettings()
        writeToLiveData()
    }

    private fun writeToLiveData() {
        _countryData.value = filterSettings?.country
        _areaData.value = filterSettings?.area
        _industryData.value = filterSettings?.industry
        _plainFiltersData.value = filterSettings?.plainFilterSettings
    }

    fun compareFilters() {
        val newFilter: FilterSettings = prepareFilterSettings()
        filterSettings?.let {
            _equalFilter.value = FiltersCompare.compareFilterSettings(newFilter, it)
            Log.d(TAG, "Compare Filters result=${_equalFilter.value}")
        }
    }

    private fun prepareFilterSettings(): FilterSettings {
        return FilterSettings(
            country = _countryData.value,
            area = _areaData.value,
            industry = _industryData.value,
            plainFilterSettings = _plainFiltersData.value
        )
    }

    private fun checkChangedFilters() {
        var value = false
        if (_industryData.value != null) value = true
        if (_countryData.value != null) value = true
        if (_areaData.value != null) value = true
        if (_plainFiltersData.value != null) value = true
        _changedFilter.value = value
    }

    fun resetFilters() {
        Log.d(TAG, "resetFilters")
        _industryData.value = null
        _countryData.value = null
        _areaData.value = null
        _plainFiltersData.value = null
        checkChangedFilters()
        saveData()
    }

    fun saveFiltersToSharedPrefs() {
        Log.d(TAG, "saveFiltersToSharedPrefs")
        filterInteractor.writeFilterSettings(prepareFilterSettings())
        loadFromShared()
        saveData()
    }

    fun saveSalaryCheckBox(isChecked: Boolean) {
        _plainFiltersData.value = PlainFilterSettings(
            expectedSalary = _plainFiltersData.value?.expectedSalary,
            notShowWithoutSalary = isChecked
        )
        DataTransfer.setPlainFilters(_plainFiltersData.value)
        compareFilters()
    }

    fun clearIndustry() {
        _industryData.value = null
        saveData()
    }

    fun clearWorkplace() {
        _countryData.value = null
        _areaData.value = null
        saveData()
    }

    fun clearSalary() {
        _plainFiltersData.value = PlainFilterSettings(
            expectedSalary = null,
            notShowWithoutSalary = _plainFiltersData.value?.notShowWithoutSalary
        )
        Log.d(TAG, "${_plainFiltersData.value}")
        saveData()
    }

    fun saveExpectedSalary(salary: String) {
        Log.d(TAG, "saveExpectedSalary=$salary")
        if (salary.isNotEmpty()) {
            _plainFiltersData.value =
                PlainFilterSettings(
                    expectedSalary = salary.toInt(),
                    notShowWithoutSalary = _plainFiltersData.value?.notShowWithoutSalary
                )
        }
        DataTransfer.setPlainFilters(_plainFiltersData.value)
        compareFilters()
    }

    fun loadData() {
        _plainFiltersData.value = DataTransfer.getPlainFilters()
        _countryData.value = DataTransfer.getCountry()
        _industryData.value = DataTransfer.getIndustry()
        _areaData.value = DataTransfer.getArea()
        checkChangedFilters()
    }

    private fun saveData() {
        DataTransfer.setPlainFilters(_plainFiltersData.value)
        DataTransfer.setCountry(_countryData.value)
        DataTransfer.setIndustry(_industryData.value)
        DataTransfer.setArea(_areaData.value)
        checkChangedFilters()
        compareFilters()
    }

    fun applyFilterSettings(filter: FilterSettings) {
        filterInteractor.writeFilterSettings(filter)
    }

    companion object {
        private val TAG = FiltersSettingsViewModel::class.java.simpleName
    }
}
