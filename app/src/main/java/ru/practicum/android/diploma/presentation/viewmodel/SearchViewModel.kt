package ru.practicum.android.diploma.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.presentation.PlaceholdersSearch

class SearchViewModel : ViewModel() {

    private val placeholderStatusMutable = MutableLiveData<PlaceholdersSearch>().apply { PlaceholdersSearch.SHOW_BLANK }
    val placeholderStatusData get() = placeholderStatusMutable

    fun setPlaceholder(placeholdersSearch: PlaceholdersSearch){
        placeholderStatusMutable.value = placeholdersSearch
    }

}
