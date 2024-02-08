package ru.practicum.android.diploma.presentation.search.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val isFilterOn: MutableLiveData<Boolean> = MutableLiveData(false)
}

