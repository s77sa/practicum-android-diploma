package ru.practicum.android.diploma.presentation.filters.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.FilterSettings
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.PlainFilterSettings
import ru.practicum.android.diploma.presentation.util.DataTransfer

class FiltersSettingsViewModel(
    private val context: Context
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

    private var expectedSalary: Int = -1
    private var notShowWithoutSalary: Boolean = false
    private var filterSettings: FilterSettings? = null

    private fun compareFilters() {
        _equalFilter.value = true
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
        expectedSalary = -1
        notShowWithoutSalary = false
        checkChangedFilters()
        saveData()
    }

    fun saveFiltersToSharedPrefs() {

    }

    fun saveSalaryCheckBox(isChecked: Boolean) {
        notShowWithoutSalary = isChecked
        saveData()
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
        val plainFilters =
            PlainFilterSettings(
                expectedSalary = -1,
                notShowWithoutSalary = notShowWithoutSalary
            )
        _plainFiltersData.value = plainFilters
        Log.d(TAG, "${_plainFiltersData.value}")
        saveData()
    }

    fun saveExpectedSalary(salary: String) {
        Log.d(TAG, "saveExpectedSalary=$salary")
        expectedSalary = if (salary.isNotEmpty()) {
            salary.toInt()
        } else {
            -1
        }
        saveData()
    }

    fun loadData() {
        val plainFilters = DataTransfer.getPlainFilters()
        if (plainFilters != null) {
            expectedSalary = plainFilters.expectedSalary
        }
        if (plainFilters != null) {
            notShowWithoutSalary = plainFilters.notShowWithoutSalary
        }
        _plainFiltersData.value = plainFilters
        _countryData.value = DataTransfer.getCountry()
        _industryData.value = DataTransfer.getIndustry()
        _areaData.value = DataTransfer.getArea()
        checkChangedFilters()
    }

    private fun saveData() {
        val plainFilters =
            PlainFilterSettings(
                expectedSalary = expectedSalary,
                notShowWithoutSalary = notShowWithoutSalary
            )
        _plainFiltersData.value = plainFilters
        DataTransfer.setPlainFilters(plainFilters)
        DataTransfer.setCountry(_countryData.value)
        DataTransfer.setIndustry(_industryData.value)
        DataTransfer.setArea(_areaData.value)
        checkChangedFilters()
    }

    companion object {
        private val TAG = FiltersSettingsViewModel::class.java.simpleName
    }
}
