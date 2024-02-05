package ru.practicum.android.diploma.presentation.filters.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.models.FilterSettings
import ru.practicum.android.diploma.domain.models.PlainFilterSettings
import ru.practicum.android.diploma.presentation.util.DataTransfer

class FiltersSettingsViewModel(context: Context) : ViewModel() {

    private val _filterSettingsData = MutableLiveData<FilterSettings?>()
    val filterSettingsData get() = _filterSettingsData

    private val _plainFiltersData = MutableLiveData<PlainFilterSettings?>()
    val plainFiltersData get() = _plainFiltersData

    private var expectedSalary: Int = -1
    private var notShowWithoutSalary: Boolean = false

    fun saveSalaryCheckBox(isChecked: Boolean) {
        notShowWithoutSalary = isChecked
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
        _plainFiltersData.value = plainFilters

    }

    private fun saveData() {
        val plainFilters =
            PlainFilterSettings(
                expectedSalary = expectedSalary,
                notShowWithoutSalary = notShowWithoutSalary
            )
        _plainFiltersData.postValue(plainFilters)
        DataTransfer.setPlainFilters(plainFilters)
    }

    companion object {
        private val TAG = FiltersSettingsViewModel::class.java.simpleName
    }
}
