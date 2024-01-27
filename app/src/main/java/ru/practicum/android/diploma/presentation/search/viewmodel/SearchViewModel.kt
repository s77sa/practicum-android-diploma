package ru.practicum.android.diploma.presentation.search.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.presentation.search.PlaceholdersEnum
import ru.practicum.android.diploma.presentation.util.debounce

class SearchViewModel : ViewModel() {

    private val placeholderStatusMutable = MutableLiveData<PlaceholdersEnum>()
    val placeholderStatusData get() = placeholderStatusMutable
    private var latestSearchText: String? = null

    fun setPlaceholder(placeholdersEnum: PlaceholdersEnum) {
        placeholderStatusMutable.value = placeholdersEnum
    }

    private val vacancySearchDebounce = debounce<String>(
        SEARCH_DEBOUNCE_DELAY,
        viewModelScope,
        true
    ) { changedText ->
        searchVacancy(changedText)
    }

    fun searchDebounce(changedText: String) {
        if (latestSearchText != changedText) {
            latestSearchText = changedText
            vacancySearchDebounce(changedText)
        }
    }

    private fun searchVacancy(changedText: String) {
        if (changedText.isNotEmpty()) {
            // ToDo Обновить состояние обсервера

            viewModelScope.launch {
                Log.i("Diploma", "searchVacancy. ToDo: Передача запроса в интерактор с текстом $changedText")
                // ToDo Обработка ответа: .collect {pair -> processResult(pair.first, pair.second)}")
            }
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

}
