package dev.yoghurt1131.weatherapi.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import java.math.BigDecimal

class CurrentWeather (
        val cityName: String,
        val status: String,
        @JsonIgnore
        val kelvin: Double,
        private var weatherIconUrl: String,
        val temperature: Double = convertTemparature(kelvin)
) {
    constructor(
            cityName: String,
            status: String,
            kelvin: Double
    ): this(cityName, status, kelvin, "") {
    }

    fun setWeatherIconUrl(iconUrl: String) {
        weatherIconUrl = iconUrl
    }

    fun getWeatherIconUrl(): String {
        return weatherIconUrl;
    }
}

const val ABSOLUTE_TEMPERATURE: Double = -273.15;

fun convertTemparature(kelvin: Double): Double {
    var decimol: BigDecimal = BigDecimal(kelvin + ABSOLUTE_TEMPERATURE);
    return decimol.setScale(1, BigDecimal.ROUND_HALF_UP).toDouble()
}