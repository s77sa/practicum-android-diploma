package ru.practicum.android.diploma.presentation.search.viewmodel

import android.content.Context
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.api.SearchInteractor
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.search.PlaceholdersSearchEnum
import ru.practicum.android.diploma.presentation.search.models.SearchState
import ru.practicum.android.diploma.presentation.util.debounce

class SearchViewModel(
    private val searchInteractor: SearchInteractor,
    private val context: Context
) : ViewModel() {

    private val stateLiveData = MutableLiveData<SearchState>()
    fun observeState(): LiveData<SearchState> = stateLiveData

    private val placeholderStatusMutable = MutableLiveData<PlaceholdersSearchEnum>(PlaceholdersSearchEnum.SHOW_BLANK)
    val placeholderStatusData get() = placeholderStatusMutable
    private var latestSearchText: String? = null
    private var isNextPageLoading = true
    private var page: Int = 0
    private var pages = 1

    private fun setPlaceholder(placeholdersSearchEnum: PlaceholdersSearchEnum) {
        placeholderStatusMutable.value = placeholdersSearchEnum
    }

    fun searchDebounce(changedText: String) {
        if (latestSearchText != changedText) {
            latestSearchText = changedText
            vacancySearchDebounce(changedText)
            page = 0
        }
    }

    private val vacancySearchDebounce = debounce<String>(
        SEARCH_DEBOUNCE_DELAY,
        viewModelScope,
        true
    ) { changedText ->
        searchVacancy(changedText, 0)
    }

    private fun searchVacancy(changedText: String, page: Int) {
        if (changedText.isNotEmpty()) {
            stateLiveData.postValue(SearchState.Loading)
            if (page == 0) {
                setPlaceholder(PlaceholdersSearchEnum.SHOW_PROGRESS_CENTER)
            } else {
                setPlaceholder(PlaceholdersSearchEnum.SHOW_PROGRESS_BOTTOM)
            }
            viewModelScope.launch {
                searchInteractor
                    .searchVacancies(
                        changedText,
                        Filter(
                            area = "113",
                            showSalary = true,
                            industry = null,
                            salary = 100_000,
                        ), page = page
                    )
                    .collect { pair ->
                        processResult(pair.first, pair.second, searchInteractor.foundItems)
                    }
            }
        }
    }

    private fun processResult(foundVacancies: List<Vacancy>?, errorMessage: String?, foundItems: Int?) {
        val vacancyList = mutableListOf<Vacancy>()
        if (foundVacancies != null) {
            vacancyList.clear()
            vacancyList.addAll(foundVacancies)
            stateLiveData.postValue(SearchState.Content(vacancyList, vacancyList.size))
            if (foundItems != null) {
                pages = foundItems / ITEMS_PER_PAGE
            }
            isNextPageLoading = false
        }

        when {
            errorMessage != null -> {
                stateLiveData.postValue(SearchState.Error(errorMessage))
                setPlaceholder(PlaceholdersSearchEnum.SHOW_NO_INTERNET)
            }

            vacancyList.isEmpty() -> {
                stateLiveData.postValue(SearchState.Empty(getString(context, R.string.no_vacancy)))
                setPlaceholder(PlaceholdersSearchEnum.SHOW_NO_VACANCY)

            }

            else -> {
                setPlaceholder(PlaceholdersSearchEnum.SHOW_RESULT)
                stateLiveData.postValue(SearchState.Content(vacancyList, foundItems = foundItems))
            }
        }
    }

    fun clearSearchResult() {
        val vacancyList = mutableListOf<Vacancy>()
        vacancyList.clear()
        stateLiveData.postValue(SearchState.Content(vacancyList, vacancyList.size))
        setPlaceholder(PlaceholdersSearchEnum.SHOW_BLANK)
    }

    fun onNextPage() {
        if (page < pages && !isNextPageLoading && !latestSearchText.isNullOrEmpty()) {
            stateLiveData.postValue(SearchState.Loading)
            page += 1
            isNextPageLoading = true
            vacancyReloadDebounce(latestSearchText!!)
        }
    }

    private val vacancyReloadDebounce = debounce<String>(
        RELOAD_DEBOUNCE_DELAY,
        viewModelScope,
        true
    ) { latestSearchText ->
        searchVacancy(latestSearchText, page)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private const val RELOAD_DEBOUNCE_DELAY = 300L
        private const val ITEMS_PER_PAGE: Int = 20
    }
}
