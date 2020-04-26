package dev.yoghurt1131.weatherapi.application.service

import dev.yoghurt1131.weatherapi.domain.City
import dev.yoghurt1131.weatherapi.domain.CurrentWeather
import dev.yoghurt1131.weatherapi.domain.Forecast

interface WeatherApiService {

    fun getCurrentWeather(cityName: String): CurrentWeather

    fun getTodayWeather(city: City): Forecast
}
