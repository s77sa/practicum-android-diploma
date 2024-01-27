package ru.practicum.android.diploma.presentation.search.viewmodel

import android.content.Context
import android.util.Log
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
            stateLiveData.postValue(SearchState.Loading)
            setPlaceholder(PlaceholdersEnum.SHOW_PROGRESS_CENTER)
            viewModelScope.launch {
                Log.i("Diploma", "searchVacancy. ToDo: Передача запроса в интерактор с текстом $changedText")
                searchInteractor
                    .searchVacancies(
                        VacancyRequest(
                            changedText,
                            area = null,
                            showSalary = true,
                            industry = null,
                            salary = 100_000,
                            page = 0
                        ).map()
                    )
                    .collect { pair ->
                        processResult(pair.first, pair.second)
                    }
            }
        }
    } // ToDo Протестить вытащить foundItems из интерактора,заменить часть параметров VacancyRequest на Фильтр")

    private fun processResult(foundVacancies: List<Vacancy>?, errorMessage: String?) {
        val vacancyList = mutableListOf<Vacancy>()
        if (foundVacancies != null) {
            vacancyList.clear()
            vacancyList.addAll(vacancyList)
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
                stateLiveData.postValue(SearchState.Content(vacancyList, foundItems = 0))
            }
        }
    } // ToDo foundItems вытащить из searchVacancy

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}
