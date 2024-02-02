package ru.practicum.android.diploma.presentation.vacancy

import android.content.Context
import androidx.core.content.ContextCompat
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy
import java.util.Locale

class FormatVacancyHTML(
    private val context: Context
) {

    fun getColorHexString(): String {
        val colorRes = R.color.blackDayWhiteNight
        return String.format(
            Locale.getDefault(),
            "#%06X",
            WHITE_COLOR and ContextCompat.getColor(context, colorRes)
        )
    }

    fun getHMLDescription(vacancy: Vacancy, currentDescription: String): String {
        return "<html>\n" +
            "        <head>\n" +
            "            <style type='text/css'>\n" +
            "                body { color: ${getColorHexString()}; }\n" +
            "            </style>\n" +
            "        </head>\n" +
            "        <body>\n" +
            "            ${currentDescription}\n" +
            "        </body>\n" +
            "    </html>"
    }

    fun formatSkillsList(skills: List<String>): String {
        val bulletPoint = "&#8226; " // HTML-код для кружочка
        return skills.joinToString("<br/>") {
            "<span style=\"color: ${getColorHexString()};\">$bulletPoint$it</span>"
        }
    }

    companion object {
        private const val WHITE_COLOR = 0xFDFDFD
    }
}
