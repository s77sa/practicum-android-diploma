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

class SelectIndustryViewModel(
    private val industryInteractor: IndustryInteractor,
    private val context: Context
) : ViewModel() {

    private var selectedIndustry: Industry? = null
    private var stateLiveData = MutableLiveData<FilterIndustryStates>()
    fun getState(): LiveData<FilterIndustryStates> = stateLiveData

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
            val result = industryInteractor.getIndustries()
            val industryList = mutableListOf<Industry>()
            if (result.first != null) {
                industryList.clear()
                industryList.addAll(result.first!!.filter { it.name.contains(s, ignoreCase = true) })
            }
            when {
                result.second != null -> {
                    if (result.second == getString(context, R.string.no_internet)) {
                        stateLiveData.postValue(FilterIndustryStates.ConnectionError)

                    } else {
                        stateLiveData.postValue(FilterIndustryStates.ServerError)
                    }
                }

                industryList.isEmpty() -> {
                    stateLiveData.postValue(FilterIndustryStates.Empty)
                }

                else -> {
                    stateLiveData.postValue(FilterIndustryStates.Success(industryList))
                }
            }


        }
    }


    fun isChecked() {

    }

    fun bufferIndustry() {
        stateLiveData.postValue(FilterIndustryStates.HasSelected)
    }

    fun getChecked(): Industry? {
        return selectedIndustry
    }

    fun saveIndustryFilter() {
        viewModelScope.launch {

        }
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
                stateLiveData.postValue(FilterIndustryStates.Success(industryList))
            }
        }
    }
}
