package ru.practicum.android.diploma.presentation.filters.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Country
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

    private var expectedSalary: Int = -1
    private var notShowWithoutSalary: Boolean = false

    fun saveSalaryCheckBox(isChecked: Boolean) {
        notShowWithoutSalary = isChecked
        saveData()
    }

    fun clearIndustry() {
        _industryData.postValue(null)
        saveData()
    }

    fun clearWorkplace() {
        _countryData.postValue(null)
        _areaData.postValue(null)
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
        _plainFiltersData.postValue(plainFilters)
        _countryData.postValue(DataTransfer.getCountry())
        _industryData.postValue(DataTransfer.getIndustry())
        _areaData.postValue(DataTransfer.getArea())
    }

    private fun saveData() {
        val plainFilters =
            PlainFilterSettings(
                expectedSalary = expectedSalary,
                notShowWithoutSalary = notShowWithoutSalary
            )
        _plainFiltersData.postValue(plainFilters)
        DataTransfer.setPlainFilters(plainFilters)
        DataTransfer.setCountry(_countryData.value)
        DataTransfer.setIndustry(_industryData.value)
        DataTransfer.setArea(_areaData.value)
    }

    companion object {
        private val TAG = FiltersSettingsViewModel::class.java.simpleName
    }
}
