package ru.practicum.android.diploma.presentation.filters.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.IndustryInteractor
import ru.practicum.android.diploma.domain.models.Industry

class SelectIndustryViewModel(
    private val context: Context,
    private val industryInteractor: IndustryInteractor
) : ViewModel() {

    private val _industryList = MutableLiveData<List<Industry>?>()
    val industryList get() = _industryList

    fun loadIndustryList() {
        // Это тестовый метод
        viewModelScope.launch {
            _industryList.value = industryInteractor
                .getIndustries().first
        }
    }
}
