package ru.practicum.android.diploma.presentation.filters.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.IndustryInteractor
import ru.practicum.android.diploma.domain.models.FilterIndustryStates
import ru.practicum.android.diploma.domain.models.Industry

class SelectIndustryViewModel(
    private val industryInteractor: IndustryInteractor
) : ViewModel() {

    private var selectedIndustry: Industry? = null
    private var stateLiveData = MutableLiveData<FilterIndustryStates>()
    fun getState(): LiveData<FilterIndustryStates> = stateLiveData

    fun getIndustries() {
        stateLiveData.postValue(FilterIndustryStates.Loading)
        viewModelScope.launch {
            stateLiveData.postValue(industryInteractor.getIndustries().first?.let { FilterIndustryStates.Success(it) })
        }
    }

    fun getIndustriesByName(industry: String) {
        stateLiveData.postValue(FilterIndustryStates.Loading)
        viewModelScope.launch {
            // industryInteractor
        }
    }

    fun isChecked() {
        viewModelScope.launch {
            // industryInteractor
        }
    }

    fun bufferIndustry(industry: Industry) {
        selectedIndustry = industry
        stateLiveData.postValue(FilterIndustryStates.HasSelected)
    }

    fun saveIndustryFilter() {
        viewModelScope.launch {
            industryInteractor
        }
    }
}
