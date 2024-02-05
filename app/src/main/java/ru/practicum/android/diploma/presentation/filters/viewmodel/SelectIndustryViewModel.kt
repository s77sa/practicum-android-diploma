package ru.practicum.android.diploma.presentation.filters.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.api.IndustryInteractor
import ru.practicum.android.diploma.domain.models.FilterSettings

class SelectIndustryViewModel(context: Context, industryInteractor: IndustryInteractor) : ViewModel() {

    private val filterSettingsMutableData = MutableLiveData<FilterSettings>()
    val filterSettingsData get() = filterSettingsMutableData

}
