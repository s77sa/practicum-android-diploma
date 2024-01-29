package ru.practicum.android.diploma.presentation.search.viewmodel

import android.content.Context
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.dto.VacancyRequest
import ru.practicum.android.diploma.domain.api.SearchInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.search.PlaceholdersEnum
import ru.practicum.android.diploma.presentation.search.models.SearchState
import ru.practicum.android.diploma.presentation.util.debounce

class SearchViewModel(
    private val searchInteractor: SearchInteractor,
    private val context: Context
) : ViewModel() {

    private val stateLiveData = MutableLiveData<SearchState>()
    fun observeState(): LiveData<SearchState> = stateLiveData

    private val placeholderStatusMutable = MutableLiveData<PlaceholdersEnum>()
    val placeholderStatusData get() = placeholderStatusMutable
    private var latestSearchText: String? = null
    private var page: Int = 0
    private var pages = 1

    fun setPlaceholder(placeholdersEnum: PlaceholdersEnum) {
        placeholderStatusMutable.value = placeholdersEnum
    }

    fun searchDebounce(changedText: String) {
        if (latestSearchText != changedText) {
            latestSearchText = changedText
            vacancySearchDebounce(changedText)
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
            setPlaceholder(PlaceholdersEnum.SHOW_PROGRESS_CENTER)
            viewModelScope.launch {
                searchInteractor
                    .searchVacancies(
                        VacancyRequest(
                            changedText,
                            area = "113",
                            showSalary = true,
                            industry = "49",
                            salary = 100_000,
                            page = page
                        ).map()
                    )
                    .collect { pair ->
                        processResult(pair.first, pair.second, searchInteractor.foundItems)
                    }
            }
        }
    } // ToDo Протестить , заменить часть параметров VacancyRequest на Фильтр")

    private fun processResult(foundVacancies: List<Vacancy>?, errorMessage: String?, foundItems: Int?) {
        val vacancyList = mutableListOf<Vacancy>()
        if (foundVacancies != null) {
            vacancyList.clear()
            vacancyList.addAll(foundVacancies)
            stateLiveData.postValue(SearchState.Content(vacancyList, vacancyList.size))
            if (foundItems != null) {
                pages = foundItems
            }
        }

        when {
            errorMessage != null -> {
                stateLiveData.postValue(SearchState.Error(errorMessage))
                setPlaceholder(PlaceholdersEnum.SHOW_NO_INTERNET)
            }

            vacancyList.isEmpty() -> {
                stateLiveData.postValue(SearchState.Empty(getString(context, R.string.no_vacancy)))
                setPlaceholder(PlaceholdersEnum.SHOW_NO_VACANCY)

            }

            else -> {
                setPlaceholder(PlaceholdersEnum.SHOW_RESULT)
                stateLiveData.postValue(SearchState.Content(vacancyList, foundItems = foundItems))
            }
        }
    }

    fun clearSearchResult() {
        val vacancyList = mutableListOf<Vacancy>()
        vacancyList.clear()
        stateLiveData.postValue(SearchState.Content(vacancyList, vacancyList.size))
        setPlaceholder(PlaceholdersEnum.SHOW_BLANK)
    }

    fun onNextPage() {
        if (page == (pages/20 + 1)) {
            stateLiveData.postValue(SearchState.Loading)
        }
        if (page < pages && !latestSearchText.isNullOrEmpty()) {
            page += 1
            searchVacancy(latestSearchText!!, page)
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}
