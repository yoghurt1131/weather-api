package dev.yoghurt1131.weatherapi.infrastructure.openweatherapi.response

data class Temperature(
    val temp: Double,
    val pressure: Int,
    val humidity: Int,
    val tempMax: Double,
    val tempMin: Double
)
