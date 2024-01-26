package ru.practicum.android.diploma.presentation.search.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.presentation.search.PlaceholdersEnum

class SearchViewModel : ViewModel() {

    private val placeholderStatusMutable = MutableLiveData<PlaceholdersEnum>()
    val placeholderStatusData get() = placeholderStatusMutable

    fun setPlaceholder(placeholdersEnum: PlaceholdersEnum) {
        placeholderStatusMutable.value = placeholdersEnum
    }

}
