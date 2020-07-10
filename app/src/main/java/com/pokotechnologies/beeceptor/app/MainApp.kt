package com.pokotechnologies.beeceptor.app

import android.app.Application
import com.pokotechnologies.beeceptor.app.di.retrofitModule
import com.pokotechnologies.beeceptor.app.di.weatherForecastModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MainApp)
            modules(listOf(retrofitModule, weatherForecastModule))
        }
    }
}