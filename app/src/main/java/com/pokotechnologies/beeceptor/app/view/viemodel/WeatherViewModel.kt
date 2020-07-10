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
import com.pokotechnologies.beeceptor.app.utils.WeatherForeCastConstants.UNIT
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class WeatherViewModel constructor(private val serviceUtil: ServiceUtil) : ViewModel() {

    private val _tempValue = MutableLiveData<Double?>()
    val tempValue: LiveData<Double?> get() = _tempValue

    private val _tempMin = MutableLiveData<Double?>()
    val tempMin: LiveData<Double?> get() = _tempMin

    private val _tempMax = MutableLiveData<Double?>()
    val tempMax: LiveData<Double?> get() = _tempMax

    private val _pressureValue = MutableLiveData<Long?>()
    val pressureValue: LiveData<Long?>
        get() = _pressureValue

    private val _humidityValue = MutableLiveData<Long?>()
    val humidityValue: LiveData<Long?> get() = _humidityValue

    private val _sunriseValue = MutableLiveData<Long?>()
    val sunriseValue: LiveData<Long?> get() = _sunriseValue

    private val _sunsetValue = MutableLiveData<Long?>()
    val sunsetValue: LiveData<Long?> get() = _sunsetValue

    private val _windSpeedValue = MutableLiveData<Long?>()
    val windSpeedValue: LiveData<Long?> get() = _windSpeedValue

    private val _weatherDescription = MutableLiveData<String?>()
    val weatherDescription: LiveData<String?> get() = _weatherDescription

    private val _updatedAt = MutableLiveData<Long?>()
    val updatedAt: LiveData<Long?> get() = _updatedAt

    private val _name = MutableLiveData<String?>()
    val name: LiveData<String?> get() = _name

    private val _country = MutableLiveData<String?>()
    val country: LiveData<String?> get() = _country

    private val _updatedAtText = MutableLiveData<String?>()
    val updatedAtText: LiveData<String?> get() = _updatedAtText


    private val _uiState = MutableLiveData<WeatherDataState?>()
    val uiState: LiveData<WeatherDataState?> get() = _uiState

    init {
        retrieveWeatherReports()
    }

    private fun retrieveWeatherReports() {
        viewModelScope.launch {
            runCatching {
                emitUiState(showProgress = true, weatherForecast = null, error = null)
                serviceUtil.weatherState(
                    apiKey = API_KEY,
                    city = CITY,
                    charset = CHARSET,
                    units = UNIT
                )
            }.onSuccess {
                emitUiState(showProgress = false, weatherForecast = Event(it), error = null)
            }.onFailure {
                it.printStackTrace()
                emitUiState(
                    showProgress = false,
                    weatherForecast = null,
                    error = Event(R.string.text_error)
                )
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


    fun setUIModel(weatherForecast: WeatherForecast?) {
         _tempValue.value = weatherForecast?.main?.temp
         _tempMin.value  =  weatherForecast?.main?.temp_min
         _tempMax.value  = weatherForecast?.main?.temp_max
         _pressureValue.value  = weatherForecast?.main?.pressure?.toLong()?:0
         _humidityValue.value  = weatherForecast?.main?.humidity?.toLong()?:0
         _sunriseValue.value  = weatherForecast?.sys?.sunrise?.toLong() ?: 0
         _sunsetValue.value = weatherForecast?.sys?.sunset?.toLong() ?: 0
         _windSpeedValue.value  = weatherForecast?.wind?.speed?.toLong()?:0
         _weatherDescription.value  = weatherForecast?.weather?.get(0)?.description
         _updatedAt.value  = weatherForecast?.dt?.toLong()?:0
        _updatedAtText.value  = "Updated at: " + SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(_updatedAt.value?:0 * 1000))

    }

}

data class WeatherDataState(
    val showProgress: Boolean,
    val weatherForecast: Event<WeatherForecast>?,
    val error: Event<Int>?
)

