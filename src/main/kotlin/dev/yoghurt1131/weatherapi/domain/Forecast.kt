package dev.yoghurt1131.weatherapi.domain

data class Forecast(
    var city: City,
    var status: WeatherStatus,
    var weatherIconUrl: String,
    var maxTemperature: Double,
    var minTemperature: Double
)
