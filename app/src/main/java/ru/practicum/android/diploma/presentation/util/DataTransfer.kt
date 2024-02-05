package ru.practicum.android.diploma.presentation.util

import ru.practicum.android.diploma.domain.models.PlainFilterSettings

object DataTransfer {

    private val TAG = DataTransfer::class.java.simpleName
    private var plainFilterSettings: PlainFilterSettings? = null

    fun getPlainFilters(): PlainFilterSettings? {
        return plainFilterSettings
    }

    fun setPlainFilters(plainFilter: PlainFilterSettings?) {
        plainFilterSettings = plainFilter
    }

}
