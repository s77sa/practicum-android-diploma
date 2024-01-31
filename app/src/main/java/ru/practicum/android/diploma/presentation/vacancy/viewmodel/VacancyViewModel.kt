package ru.practicum.android.diploma.presentation.vacancy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.FavouriteInteractor
import ru.practicum.android.diploma.domain.api.VacancyInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.util.Resource
import ru.practicum.android.diploma.presentation.vacancy.models.VacancyScreenState

class VacancyViewModel(
    private val vacancyInteractor: VacancyInteractor,
    private val favouriteInteractor: FavouriteInteractor,
) : ViewModel() {

    private val _isFavourite = MutableLiveData<Boolean>()
    val isFavourite: LiveData<Boolean> get() = _isFavourite

    private val _vacancyScreenState = MutableLiveData<VacancyScreenState>()
    val vacancyScreenState: LiveData<VacancyScreenState> get() = _vacancyScreenState

    private val _jobDescriptionHtml = MutableLiveData<String>()
    val jobDescriptionHtml: LiveData<String> get() = _jobDescriptionHtml
    fun getVacancyDetailsById(id: String) {
        _vacancyScreenState.value = VacancyScreenState.Loading

        viewModelScope.launch {
            when (val result = vacancyInteractor.getDetailsById(id)) {
                is Resource.Success -> {
                    _vacancyScreenState.value = VacancyScreenState.Success(result.data!!)
                }

                is Resource.Error -> {
                    _vacancyScreenState.value = result.message?.let { VacancyScreenState.Error(it) }
                }
            }
        }
    }

    fun shareVacancy(id: String?) {
        viewModelScope.launch {
            vacancyInteractor.shareVacancy(HH_URL + id)
        }
    }

    fun sendEmail(address: String, subject: String, text: String) {
        viewModelScope.launch {
            vacancyInteractor.sendEmail(address, subject, text)
        }
    }

    fun makeCall(number: String) {
        viewModelScope.launch {
            vacancyInteractor.makeCall(number)
        }
    }

    fun checkFavouriteStatus(vacancyId: String) {
        viewModelScope.launch {
            val favourites = favouriteInteractor.getFavourite(vacancyId).firstOrNull()
            val isFavourite = favourites?.isNotEmpty() ?: false
            _isFavourite.value = isFavourite
        }
    }

    fun toggleFavouriteStatus(vacancy: Vacancy) {
        viewModelScope.launch {
            if (isFavourite.value == true) {
                favouriteInteractor.deleteFavourite(vacancy)
            } else {
                favouriteInteractor.addFavourite(vacancy)
            }
            _isFavourite.value = !_isFavourite.value!!
        }
    }
    fun loadJobDescription(vacancy: Vacancy, colorHexString: String) {
        viewModelScope.launch {
            val jobDescriptionHtml =
                "<html>\n" +
                    "        <head>\n" +
                    "            <style type='text/css'>\n" +
                    "                body { color: $colorHexString; }\n" +
                    "            </style>\n" +
                    "        </head>\n" +
                    "        <body>\n" +
                    "            ${vacancy.description}\n" +
                    "        </body>\n" +
                    "    </html>"
            _jobDescriptionHtml.value = jobDescriptionHtml
        }
    }
    companion object {
        const val HH_URL = "https://hh.ru/vacancy/"
    }
}
