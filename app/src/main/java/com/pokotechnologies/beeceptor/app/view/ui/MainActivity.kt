package com.pokotechnologies.beeceptor.app.view.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.pokotechnologies.beeceptor.R
import com.pokotechnologies.beeceptor.app.utils.WeatherForeCastConstants
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
        setDataUI()
    }

    private fun observeData() {
        weatherViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            loader.visibility = if (dataState.showProgress) View.VISIBLE else View.GONE
            if (dataState.weatherForecast != null && !dataState.weatherForecast.consumed)
                dataState.weatherForecast.consume()?.let { dataState ->
                    println(dataState)
                    weatherViewModel.setUIModel(dataState)
                }
            if (dataState.error != null && !dataState.error.consumed)
                dataState.error.consume()?.let { errorResource ->
                    Toast.makeText(this, resources.getString(errorResource), Toast.LENGTH_SHORT)
                        .show()
                    // handle error state
                }
        })
    }

    private fun setDataUI() {
        address.text = weatherViewModel.name.value + " " +weatherViewModel.country.value
        updated_at.text = weatherViewModel.updatedAt.value.toString()
        status.text = weatherViewModel.weatherDescription?.value
        temp.text = weatherViewModel.tempValue.value.toString()
        temp_min.text =  weatherViewModel.tempMin.value.toString()
        temp_max.text =  weatherViewModel.tempMax.value.toString()
        sunrise.text = SimpleDateFormat(WeatherForeCastConstants.DateFormat, Locale.ENGLISH).format(
            Date( weatherViewModel.sunriseValue.value?:0 * 1000)
        )
        sunset.text = SimpleDateFormat(WeatherForeCastConstants.DateFormat, Locale.ENGLISH).format(
            Date( weatherViewModel.sunsetValue.value?:0 * 1000)
        )
        wind.text =  weatherViewModel.windSpeedValue.value.toString()
        pressure.text =  weatherViewModel.pressureValue.value.toString()
        humidity.text =  weatherViewModel.humidityValue.value.toString()
    }
}

