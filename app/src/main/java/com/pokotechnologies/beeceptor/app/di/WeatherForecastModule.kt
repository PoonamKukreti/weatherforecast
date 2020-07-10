package com.pokotechnologies.beeceptor.app.di

import com.pokotechnologies.beeceptor.app.view.viemodel.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
val weatherForecastModule = module {

    viewModel {
        WeatherViewModel(get())
    }

}