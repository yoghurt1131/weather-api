package dev.yoghurt1131.weatherapi.domain.output.valueobject

data class Forecast(
    var cityName: String,
    var status: String,
    var weatherIconUrl: String,
    var maxTemperature: Double,
    var minTemperature: Double
)
