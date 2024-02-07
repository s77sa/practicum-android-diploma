package ru.practicum.android.diploma.presentation.filters.viewmodel

import android.content.Context
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
    private var stateLiveData = MutableLiveData<FilterIndustryStates>()
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
        stateLiveData.postValue(FilterIndustryStates.Loading)

        viewModelScope.launch {
            val result = industryInteractor.getIndustries()
            processResult(result.first, result.second)
        }
    }

    fun filter(s: String) {
        stateLiveData.postValue(FilterIndustryStates.Loading)
        viewModelScope.launch {
            val filteredIndustry = foundIndustry?.filter { it.name.contains(s, ignoreCase = true) }
            if (filteredIndustry!!.isEmpty()) {
                stateLiveData.postValue(FilterIndustryStates.Empty)
            } else {
                stateLiveData.postValue(FilterIndustryStates.Success(filteredIndustry))
            }

        }
    }

    fun isChecked() {
        // Empty
    }

    fun bufferIndustry() {
        stateLiveData.postValue(FilterIndustryStates.HasSelected)
    }

    fun getChecked(): Industry? {
        return selectedIndustry
    }

    fun saveIndustryFilter(selectedIndustry: Industry) {
        dataTransfer.setIndustry(selectedIndustry)
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
                    stateLiveData.postValue(FilterIndustryStates.ConnectionError)

                } else {
                    stateLiveData.postValue(FilterIndustryStates.ServerError)

                }

            }

            industryList.isEmpty() -> {
                stateLiveData.postValue(FilterIndustryStates.Empty)
            }

            else -> {
                foundIndustry = industryList
                stateLiveData.postValue(FilterIndustryStates.Success(industryList))
            }
        }
    }
}
