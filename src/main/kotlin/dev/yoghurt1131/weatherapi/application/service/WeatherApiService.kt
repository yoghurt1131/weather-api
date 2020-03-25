package dev.yoghurt1131.weatherapi.application.service

import dev.yoghurt1131.weatherapi.domain.CurrentWeather
import dev.yoghurt1131.weatherapi.domain.output.valueobject.Forecast

interface WeatherApiService {

    fun getCurrentWeather(cityName: String): CurrentWeather;

    fun getTodayWeather(citiName: String): Forecast;
}