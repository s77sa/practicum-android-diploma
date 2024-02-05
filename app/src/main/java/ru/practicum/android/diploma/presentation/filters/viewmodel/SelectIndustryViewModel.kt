package ru.practicum.android.diploma.presentation.filters.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.IndustryInteractor
import ru.practicum.android.diploma.domain.models.FilterIndustryStates
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.presentation.util.Resource

class SelectIndustryViewModel(
    private val industryInteractor: IndustryInteractor
) : ViewModel() {

    private var selectedIndustry: Industry? = null
    private val stateLiveData = MutableLiveData<FilterIndustryStates>()
    fun getState(): LiveData<FilterIndustryStates> = stateLiveData

    fun getIndustries() {
        stateLiveData.postValue(FilterIndustryStates.Loading)
        viewModelScope.launch {
            industryInteractor//
        }
    }


    fun getIndustriesByName(industry: String) {
        stateLiveData.postValue(FilterIndustryStates.Loading)
        viewModelScope.launch {
            industryInteractor//
        }
    }

    fun isChecked() {
        viewModelScope.launch {
            industryInteractor//
        }
    }

    private fun postIndustry(dto: Resource<List<Industry>>) {
        when (dto) {
            is Resource.Error -> {
                stateLiveData.postValue(FilterIndustryStates.ServerError)
            }

            is Resource.Success -> {
                if (dto.data!!.size > 0) {
                    stateLiveData.postValue(FilterIndustryStates.Success(dto.data))
                } else {
                    stateLiveData.postValue(FilterIndustryStates.Empty)
                }
            }
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
