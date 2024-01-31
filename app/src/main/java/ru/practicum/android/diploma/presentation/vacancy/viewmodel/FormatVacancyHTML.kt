package ru.practicum.android.diploma.presentation.vacancy.viewmodel

import android.content.Context
import androidx.core.content.ContextCompat
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy
import java.util.Locale

private const val WHITE_COLOR = 0xFDFDFD
fun getColorHexString(context: Context): String {
    val colorRes = R.color.blackDayWhiteNight
    return String.format(
        Locale.getDefault(),
        "#%06X",
        WHITE_COLOR and ContextCompat.getColor(context, colorRes)
    )
}

fun getHMLDescription(vacancy: Vacancy, currentDescription: String, context: Context): String {
    return "<html>\n" +
        "        <head>\n" +
        "            <style type='text/css'>\n" +
        "                body { color: ${getColorHexString(context)}; }\n" +
        "            </style>\n" +
        "        </head>\n" +
        "        <body>\n" +
        "            ${currentDescription}\n" +
        "        </body>\n" +
        "    </html>"
}

fun formatSkillsList(skills: List<String>, context: Context): String {
    val bulletPoint = "&#8226; " // HTML-код для кружочка
    return skills.joinToString("<br/>") {
        "<span style=\"color: ${getColorHexString(context)};\">$bulletPoint$it</span>"
    }
}
