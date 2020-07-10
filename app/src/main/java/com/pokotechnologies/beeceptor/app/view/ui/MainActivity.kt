package com.pokotechnologies.beeceptor.app.view.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.pokotechnologies.beeceptor.R
import com.pokotechnologies.beeceptor.app.utils.WeatherForeCastConstants
import com.pokotechnologies.beeceptor.app.view.viemodel.WeatherDataState
import com.pokotechnologies.beeceptor.app.view.viemodel.WeatherViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private val weatherViewModel: WeatherViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeData()
    }

    private fun observeData() {
        weatherViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            loader.visibility = if (dataState.showProgress) View.VISIBLE else View.GONE
            if (dataState.weatherForecast != null && !dataState.weatherForecast.consumed)
                dataState.weatherForecast.consume()?.let { dataState ->
                    println(dataState)
                    setDataUI(it)
                }
            if (dataState.error != null && !dataState.error.consumed)
                dataState.error.consume()?.let { errorResource ->
                    Toast.makeText(this, resources.getString(errorResource), Toast.LENGTH_SHORT)
                        .show()
                    // handle error state
                }
        })
    }

    private fun setDataUI(it: WeatherDataState) {

        val tempValue = it.weatherForecast?.consume()?.main?.temp.toString() + "°C"
        val tempMin = "Min Temp: " + it.weatherForecast?.consume()?.main?.temp_min.toString() + "°C"
        val tempMax = "Max Temp: " + it.weatherForecast?.consume()?.main?.temp_max.toString() + "°C"
        val pressureValue = it.weatherForecast?.consume()?.main?.pressure.toString()
        val humidityValue = it.weatherForecast?.consume()?.main?.humidity.toString()

        val sunriseValue: Long = it.weatherForecast?.consume()?.sys?.sunrise?.toLong() ?: 0
        val sunsetValue: Long = it.weatherForecast?.consume()?.sys?.sunset?.toLong() ?: 0
        val windSpeedValue = it.weatherForecast?.consume()?.wind?.speed.toString()
        val weatherDescription = it.weatherForecast?.consume()?.weather?.get(0)?.description
        val updatedAt: Long = it.weatherForecast?.consume()?.dt?.toLong() ?: 0
        val updatedAtText =
            "Updated at: " + SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(
                Date(updatedAt * 1000)
            )
        address.text =
            it.weatherForecast?.consume()?.name + " " + it.weatherForecast?.consume()?.sys?.country
        updated_at.text = updatedAtText
        status.text = weatherDescription?.capitalize()
        temp.text = tempValue
        temp_min.text = tempMin
        temp_max.text = tempMax
        sunrise.text = SimpleDateFormat(WeatherForeCastConstants.DateFormat, Locale.ENGLISH).format(
            Date(sunriseValue * 1000)
        )
        sunset.text = SimpleDateFormat(WeatherForeCastConstants.DateFormat, Locale.ENGLISH).format(
            Date(sunsetValue * 1000)
        )
        wind.text = windSpeedValue
        pressure.text = pressureValue
        humidity.text = humidityValue
    }
}

