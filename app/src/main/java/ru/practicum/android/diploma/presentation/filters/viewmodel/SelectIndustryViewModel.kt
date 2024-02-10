package ru.practicum.android.diploma.presentation.filters.viewmodel

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.api.IndustryInteractor
import ru.practicum.android.diploma.domain.models.FilterIndustryStates
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.presentation.filters.SelectIndustryFragment
import ru.practicum.android.diploma.presentation.util.DataTransfer
import ru.practicum.android.diploma.presentation.util.debounce

class SelectIndustryViewModel(
    private val industryInteractor: IndustryInteractor,
    private val context: Context,
    private val dataTransfer: DataTransfer
) : ViewModel() {
    private var selectedIndustry: Industry? = null
    private var foundIndustry: MutableList<Industry>? = null

    private val _stateLiveData = MutableLiveData<FilterIndustryStates>()
    val stateLiveData: LiveData<FilterIndustryStates> get() = _stateLiveData

    fun getState(): LiveData<FilterIndustryStates> = stateLiveData
    fun searchDebounce(changedText: String) {
        industrySearchDebounce(changedText)
    }

    private val industrySearchDebounce = debounce<String>(
        SelectIndustryFragment.SEARCH_DEBOUNCE_DELAY_MILS,
        viewModelScope,
        true
    ) { changedText ->
        filter(changedText)
    }

    fun getIndustries() {
        _stateLiveData.postValue(FilterIndustryStates.Loading)
        viewModelScope.launch {
            val result = industryInteractor.getIndustries()
            processResult(result.first, result.second)
        }
    }

    fun filter(s: String) {
        _stateLiveData.postValue(FilterIndustryStates.Loading)
        viewModelScope.launch {
            val filteredIndustry = foundIndustry?.filter { it.name.contains(s, ignoreCase = true) }
            if (filteredIndustry.isNullOrEmpty()) {
                _stateLiveData.postValue(FilterIndustryStates.Empty)
            } else {
                _stateLiveData.postValue(FilterIndustryStates.Success(filteredIndustry))
            }
        }
    }

    fun isChecked() {
        // Empty
    }

    fun bufferIndustry() {
        _stateLiveData.postValue(FilterIndustryStates.HasSelected)
    }

    fun getChecked(): Industry? {
        return selectedIndustry
    }

    fun saveIndustryFilter(selectedIndustry: Industry) {
        dataTransfer.setIndustry(selectedIndustry)
        Log.d(TAG, "saveIndustryFilter selectedIndustry=${selectedIndustry.name}")
    }

    private fun processResult(found: List<Industry>?, errorMessage: String?) {
        val industryList = mutableListOf<Industry>()
        if (found != null) {
            industryList.clear()
            industryList.addAll(found)
        }
        when {
            errorMessage != null -> {
                if (errorMessage == getString(context, R.string.no_internet)) {
                    _stateLiveData.postValue(FilterIndustryStates.ConnectionError)
                } else {
                    _stateLiveData.postValue(FilterIndustryStates.ServerError)
                }
            }

            industryList.isEmpty() -> {
                _stateLiveData.postValue(FilterIndustryStates.Empty)
            }

            else -> {
                foundIndustry = industryList
                _stateLiveData.postValue(FilterIndustryStates.Success(industryList))
            }
        }
    }

    companion object {
        const val TAG = "SelectIndustryViewModel"
    }
}
