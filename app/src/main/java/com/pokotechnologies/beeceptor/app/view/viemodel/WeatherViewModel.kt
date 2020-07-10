package com.pokotechnologies.beeceptor.app.view.viemodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pokotechnologies.beeceptor.R
import com.pokotechnologies.beeceptor.app.model.WeatherForecast
import com.pokotechnologies.beeceptor.app.utils.Event
import com.pokotechnologies.beeceptor.app.utils.ServiceUtil
import com.pokotechnologies.beeceptor.app.utils.WeatherForeCastConstants.API_KEY
import com.pokotechnologies.beeceptor.app.utils.WeatherForeCastConstants.CHARSET
import com.pokotechnologies.beeceptor.app.utils.WeatherForeCastConstants.CITY
import kotlinx.coroutines.launch

class WeatherViewModel constructor(private val serviceUtil: ServiceUtil) : ViewModel() {

    private val _uiState = MutableLiveData<WeatherDataState?>()
    val uiState: LiveData<WeatherDataState?> get() = _uiState

    init {
        retrieveWeatherReports()
    }

    private fun retrieveWeatherReports() {
        viewModelScope.launch {
            runCatching {
                emitUiState(showProgress = true,weatherForecast =  null,error = null)
                serviceUtil.weatherState(apiKey = API_KEY,city = CITY,charset = CHARSET)
            }.onSuccess {
                emitUiState(showProgress = false,weatherForecast = Event(it),error = null)
            }.onFailure {
                it.printStackTrace()
                emitUiState(showProgress = false,weatherForecast = null,error = Event(R.string.text_error))
            }
        }
    }

    private fun emitUiState(
        showProgress: Boolean = false,
        weatherForecast: Event<WeatherForecast>?,
        error: Event<Int>?
    ) {
        val dataState = WeatherDataState(showProgress, weatherForecast, error)
        _uiState.value = dataState
    }
}

data class WeatherDataState(
    val showProgress: Boolean,
    val weatherForecast: Event<WeatherForecast>?,
    val error: Event<Int>?
)
