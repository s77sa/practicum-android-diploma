package ru.practicum.android.diploma.presentation.util

import android.content.Context
import ru.practicum.android.diploma.R
import java.text.NumberFormat
import java.util.Locale

class SalaryUtils {
    companion object {
        fun formatSalary(context: Context, salaryFrom: Int?, salaryTo: Int?, currency: String?): String {
            val fromText = context.getString(R.string.from)
            val toText = context.getString(R.string.to)
            val currencySymbol = getCurrencySymbol(currency)

            val numberFormat = NumberFormat.getInstance(Locale("ru", "RU"))

            return when {
                salaryFrom != null && salaryTo != null -> {
                    "$fromText ${numberFormat.format(salaryFrom)} $toText ${numberFormat.format(salaryTo)} $currencySymbol"
                }
                salaryFrom != null -> {
                    "$fromText ${numberFormat.format(salaryFrom)} $currencySymbol"
                }
                salaryTo != null -> {
                    "$toText ${numberFormat.format(salaryTo)} $currencySymbol"
                }
                else -> context.getString(R.string.salary_not_specified)
            }
        }

        private fun getCurrencySymbol(currencyCode: String?): String {
            return when (currencyCode) {
                "RUR" -> "₽"
                "EUR" -> "€"
                "USD" -> "$"
                else -> currencyCode ?: ""
            }
        }
    }
}
