package ru.practicum.android.diploma

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.practicum.android.diploma.di.dbModule
import ru.practicum.android.diploma.di.favouriteModule
import ru.practicum.android.diploma.di.filterModule
import ru.practicum.android.diploma.di.repositoryModule
import ru.practicum.android.diploma.di.searchModule
import ru.practicum.android.diploma.di.viewModelModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(
                listOf(
                    searchModule,
                    dbModule,
                    viewModelModule,
                    repositoryModule,
                    favouriteModule,
                    filterModule
                )
            )
        }
    }
}
