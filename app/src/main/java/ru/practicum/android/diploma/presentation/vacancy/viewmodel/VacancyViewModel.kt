package ru.practicum.android.diploma.presentation.vacancy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.VacancyInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.util.Resource
import ru.practicum.android.diploma.presentation.vacancy.models.VacancyScreenState

class VacancyViewModel(private val interactor: VacancyInteractor) : ViewModel() {

    private val _vacancyDetails = MutableLiveData<Resource<Vacancy>>()
    val vacancyDetails: LiveData<Resource<Vacancy>> get() = _vacancyDetails
    private val _vacancyScreenState = MutableLiveData<VacancyScreenState>()
    val vacancyScreenState: LiveData<VacancyScreenState> get() = _vacancyScreenState

    fun getVacancyDetailsById(id: String) {
        _vacancyScreenState.value = VacancyScreenState.Loading

        viewModelScope.launch {
            when (val result = interactor.getDetailsById(id)) {
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
        interactor.shareVacancy(HH_URL + id)
    }

    companion object {
        const val HH_URL = "https://hh.ru/vacancy/"
    }
}
