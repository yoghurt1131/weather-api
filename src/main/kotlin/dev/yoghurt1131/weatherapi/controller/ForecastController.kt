package dev.yoghurt1131.weatherapi.controller

import dev.yoghurt1131.weatherapi.application.service.WeatherApiService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/forecast")
class ForecastController(private val weatherApiService: WeatherApiService) {

    @GetMapping("/daily")
    fun dailyForecast(cityName: String) = weatherApiService.getTodaysWeather(cityName);

}