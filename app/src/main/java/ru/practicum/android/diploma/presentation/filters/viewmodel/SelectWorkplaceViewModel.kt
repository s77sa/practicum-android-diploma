package ru.practicum.android.diploma.presentation.filters.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.models.FilterSettings

class SelectWorkplaceViewModel(context: Context) : ViewModel() {

    private val filterSettingsMutableData = MutableLiveData<FilterSettings>()
    val filterSettingsData get() = filterSettingsMutableData

}
