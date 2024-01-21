package ru.practicum.android.diploma.util

import androidx.appcompat.app.AppCompatDelegate

object ThemeSwitcher {
    fun switchTheme(isDark: Boolean = false) {
        AppCompatDelegate.setDefaultNightMode(
            if (isDark) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}
